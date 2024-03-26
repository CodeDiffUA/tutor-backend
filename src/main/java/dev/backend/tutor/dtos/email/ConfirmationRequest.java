package dev.backend.tutor.dtos.email;

public record ConfirmationRequest(
        String email,
        String token
) {
}
