package org.school.stylish.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.school.stylish.dao.RoleDao;
import org.school.stylish.util.FacebookUtil;
import org.school.stylish.dao.UserDao;
import org.school.stylish.dto.other.ApiResponse;
import org.school.stylish.dto.other.ErrorResponse;
import org.school.stylish.dto.user.*;
import org.school.stylish.util.JwtUtil;
import org.school.stylish.dto.user.facebook.FacebookSignInDTO;
import org.school.stylish.dto.user.facebook.FacebookUserDTO;
import org.school.stylish.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String SOCIAL_LOGIN_PASSWORD = "SOCIAL_LOGIN_NO_PASSWORD";

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final FacebookUtil facebookUtil;
    private final RoleDao roleDao;


    @Override
    public ResponseEntity<?> signUp(SignUpDTO signUpDTO) {
        try {
            if (emailExists(signUpDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("Email already exists"));
            }

            UserDTO user = registerUser(signUpDTO);
            log.info("User registered: {}", user);

            String token = jwtUtil.generateToken(user);

            UserResponseDTO response = new UserResponseDTO(
                    token,
                    jwtUtil.getExpirationTime(),
                    user
            );

            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            log.error("Server error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server error"));
        }
    }

    @Override
    public ResponseEntity<?> signIn(UserDTO userDTO) {
        try {
            switch (userDTO.getProvider().toLowerCase()) {
                case "native":
                    SignInDTO signInDTO = new SignInDTO();
                    signInDTO.setProvider(userDTO.getProvider());
                    signInDTO.setEmail(userDTO.getEmail());
                    signInDTO.setPassword(userDTO.getPassword());
                    return nativeSignIn(signInDTO);
                case "facebook":
                    FacebookSignInDTO facebookSignInDTO = new FacebookSignInDTO();
                    facebookSignInDTO.setProvider(userDTO.getProvider());
                    facebookSignInDTO.setAccessToken(userDTO.getAccessToken());
                    return facebookSignIn(facebookSignInDTO);
                default:
                    return ResponseEntity.badRequest()
                            .body(new ErrorResponse("Invalid provider: " + userDTO.getProvider()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server error"));
        }
    }


    @Override
    public ResponseEntity<?> nativeSignIn(SignInDTO signInDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));

            UserDTO user = getUserByEmail(signInDTO.getEmail());
            String token = generateTokenWithRoles(user);

            UserResponseDTO response = new UserResponseDTO(
                    token,
                    jwtUtil.getExpirationTime(),
                    user
            );

            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (BadCredentialsException e) {
            log.error("Sign in failed for user: {}", signInDTO.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Sign In Failed"));
        } catch (Exception e) {
            log.error("Server error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server error"));
        }
    }

    @Override
    public ResponseEntity<?> facebookSignIn(FacebookSignInDTO facebookSignInDTO) {
        log.info("Received DTO: {}", facebookSignInDTO);
        try {
            // connect to facebook verify access token.
            if (!facebookUtil.verifyAccessToken(facebookSignInDTO.getAccessToken())) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Invalid access token"));
            }

            log.info("Access token verification passed.");

            FacebookUserDTO facebookUserDTO = facebookUtil.getUserInfo(facebookSignInDTO.getAccessToken());

            log.info("Retrieved Facebook user info: {}", facebookUserDTO);

            UserDTO user = getUserByEmail(facebookUserDTO.getEmail());

            if (user == null) {
                log.info("User not found. Creating a new user.");
                user = registerFacebookUser(facebookUserDTO);
            }

            String token = generateTokenWithRoles(user);
            UserResponseDTO response = new UserResponseDTO(
                    token,
                    jwtUtil.getExpirationTime(),
                    user
            );

            log.info("Successfully generated JWT token.");

            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error processing Facebook login: " + e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);

            ProfileDTO user = getUserProfile(token);

            return ResponseEntity.ok(new ApiResponse<>(user));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error"));
        }
    }

    @Override
    public UserDTO registerUser(SignUpDTO signUpDTO) {
        // encode password before saving
        String hashPassword = passwordEncoder.encode(signUpDTO.getPassword());
        signUpDTO.setPassword(hashPassword);

        // Those registered using this method are defaulted to native
        if (signUpDTO.getProvider() == "facebook") {
            signUpDTO.setProvider("facebook");
            signUpDTO.setPassword(SOCIAL_LOGIN_PASSWORD);
        } else {
            signUpDTO.setProvider("native");
        }

        if (signUpDTO.getPicture() == null) {
            signUpDTO.setPicture("default_url");
        }

        UserDTO newUser = userDao.InsertUser(signUpDTO);

        RoleDTO userRole = roleDao.findByName("ROLE_USER");
        if(userRole != null) {
            userDao.addRoleToUser(newUser.getId(), userRole.getRoleId());
        }

        return newUser;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public boolean emailExists(String email) {
        return userDao.findUserByEmail(email) != null;
    }

    @Override
    public ProfileDTO getUserProfile(String token) {
        return jwtUtil.CreateUserFromToken(token);
    }

    private UserDTO registerFacebookUser(FacebookUserDTO facebookUserDTO) {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setProvider("facebook");
        signUpDTO.setName(facebookUserDTO.getName());
        signUpDTO.setEmail(facebookUserDTO.getEmail());
        log.info(facebookUserDTO.getProvider());
        log.info(signUpDTO);

        if (facebookUserDTO.getPicture() != null
                && facebookUserDTO.getPicture().getData() != null) {
            signUpDTO.setPicture(facebookUserDTO.getPicture().getData().getUrl());
        }

        signUpDTO.setPassword(SOCIAL_LOGIN_PASSWORD);

        return registerUser(signUpDTO);
    }

    private String generateTokenWithRoles(UserDTO user) {
        Set<RoleDTO> roles = userDao.getUserRoles(user.getId());
        return jwtUtil.generateTokenWithRoles(user, roles);
    }
}
