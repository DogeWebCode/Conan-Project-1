package org.school.stylish.service;

import org.school.stylish.dto.user.*;
import org.school.stylish.dto.user.facebook.FacebookSignInDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserDTO registerUser(SignUpDTO singUpDTO);

    UserDTO getUserByEmail(String email);

    boolean emailExists(String email);

    ProfileDTO getUserProfile(String token);

    ResponseEntity<?> signUp(SignUpDTO signUpDTO);
    
    ResponseEntity<?> signIn(UserDTO userDTO);

    ResponseEntity<?> nativeSignIn(SignInDTO signInDTO);

    ResponseEntity<?> facebookSignIn(FacebookSignInDTO facebookSignInDTO);

    ResponseEntity<?> getProfile(String authHeader);

}
