package dev.backend.tutor.security.tokensAuth.factories;

import dev.backend.tutor.security.tokensAuth.tokens.AccessToken;
import dev.backend.tutor.security.tokensAuth.tokens.Token;

public interface AccessTokenFactory {

    AccessToken createToken(Token accessToken);
}
