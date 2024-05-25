package dev.backend.tutor.services.security.updateToken;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;

public interface UpdateTokenService {


    JwtAndRefreshDto updateRefreshTokenToken(UpdateJwtTokenRequest updateJwtTokenRequest) throws InvalidTokenException, NotFoundUserException;
}
