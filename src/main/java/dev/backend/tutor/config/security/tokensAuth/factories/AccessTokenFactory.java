package dev.backend.tutor.config.security.tokensAuth.factories;

import dev.backend.tutor.config.security.tokensAuth.tokens.AccessToken;
import dev.backend.tutor.config.security.tokensAuth.tokens.Token;

public interface AccessTokenFactory {

    AccessToken createToken(Token accessToken);
}
