package org.school.stylish.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.school.stylish.dto.user.*;
import org.school.stylish.service.UserService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/1.0/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Use consumes to specify the content type of the request.
    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        return userService.signUp(signUpDTO);
    }

    @PostMapping(value = "/signin", consumes = "application/json")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserDTO userDTO) {
        return userService.signIn(userDTO);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        return userService.getProfile(authHeader);
    }
}