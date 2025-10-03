package com.samirdev.spring_chat_app.domain.auth.register;

import jakarta.validation.constraints.*;

public record RegisterResponseDTO(
        Boolean saved
) {}
