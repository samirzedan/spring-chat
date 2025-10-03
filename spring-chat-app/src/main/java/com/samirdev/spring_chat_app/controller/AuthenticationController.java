package com.samirdev.spring_chat_app.controller;

import com.samirdev.spring_chat_app.domain.auth.login.LoginRequestDTO;
import com.samirdev.spring_chat_app.domain.auth.login.LoginResponseDTO;
import com.samirdev.spring_chat_app.domain.auth.register.RegisterRequestDTO;
import com.samirdev.spring_chat_app.domain.auth.register.RegisterResponseDTO;
import com.samirdev.spring_chat_app.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        LoginResponseDTO response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO request) {
        RegisterResponseDTO response = authenticationService.register(request);
        if (!response.saved()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
