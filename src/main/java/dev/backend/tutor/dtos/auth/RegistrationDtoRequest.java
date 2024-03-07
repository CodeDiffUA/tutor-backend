package dev.backend.tutor.dtos.auth;

import dev.backend.tutor.utills.student.Form;

public record RegistrationDtoRequest(
        String username,
        String email,
        String password,
        Form form,
        Integer age
) {}
