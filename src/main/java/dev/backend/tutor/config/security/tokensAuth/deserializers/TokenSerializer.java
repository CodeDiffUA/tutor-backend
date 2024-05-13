package dev.backend.tutor.config.security.tokensAuth.deserializers;

import dev.backend.tutor.config.security.tokensAuth.tokens.AccessToken;
import dev.backend.tutor.config.security.tokensAuth.tokens.Token;

public interface TokenSerializer {

    String serialize(Token accessToken);
}
