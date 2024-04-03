package dev.backend.tutor.dtos.forgotPassword;

public record ForgotPasswordDtoRequest(
        String password,
        String email
) {
}
