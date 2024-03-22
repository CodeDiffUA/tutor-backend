package dev.backend.tutor.dtos.auth;

public record AuthenticationDtoRequest (
        String usernameOrEmail, String password
){
}
