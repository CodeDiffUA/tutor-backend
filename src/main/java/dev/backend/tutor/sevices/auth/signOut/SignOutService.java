package dev.backend.tutor.sevices.auth.signOut;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.exceptions.InvalidTokenException;

public interface SignOutService {

    public void signOut(String refreshStringToken) throws InvalidTokenException;
}
