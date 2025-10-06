package com.samirdev.spring_chat_app.service;

import com.samirdev.spring_chat_app.domain.auth.login.LoginRequestDTO;
import com.samirdev.spring_chat_app.domain.auth.login.LoginResponseDTO;
import com.samirdev.spring_chat_app.domain.auth.register.RegisterRequestDTO;
import com.samirdev.spring_chat_app.domain.auth.register.RegisterResponseDTO;
import com.samirdev.spring_chat_app.domain.user.User;
import com.samirdev.spring_chat_app.core.security.TokenService;
import com.samirdev.spring_chat_app.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            TokenService tokenService,
            UserRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponseDTO login(LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return new LoginResponseDTO(token);
    }

    public RegisterResponseDTO register(RegisterRequestDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) {
            return new RegisterResponseDTO(false);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.name(), data.email(), encryptedPassword);

        this.userRepository.save(user);
        return new RegisterResponseDTO(true);
    }
}
