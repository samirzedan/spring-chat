package com.samirdev.spring_chat_app.controller;

import com.samirdev.spring_chat_app.domain.user.User;
import com.samirdev.spring_chat_app.domain.user.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(@AuthenticationPrincipal User authenticatedUser) {
        UserResponseDTO response = new UserResponseDTO(authenticatedUser);
        return ResponseEntity.ok(response);
    }
}
