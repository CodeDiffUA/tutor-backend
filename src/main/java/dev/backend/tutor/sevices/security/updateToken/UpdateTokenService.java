package dev.backend.tutor.sevices.security.updateToken;

import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;

public interface UpdateTokenService {


    AuthenticationResponseDto updateRefreshTokenToken(UpdateJwtTokenRequest updateJwtTokenRequest) throws InvalidTokenException, NotFoundUserException;
}
