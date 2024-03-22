package dev.backend.tutor.sevices.authentication;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.WrongPasswordOrUsernameException;

public interface AuthenticationService{

    AuthenticationResponseDto signIn(AuthenticationDtoRequest authenticationDtoRequest) throws WrongPasswordOrUsernameException, NotFoundUserException;
}
