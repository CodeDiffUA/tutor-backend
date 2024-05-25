package dev.backend.tutor.services.auth.signOut;

import dev.backend.tutor.exceptions.InvalidTokenException;

public interface SignOutService {

    public void signOut(String refreshStringToken) throws InvalidTokenException;
}
