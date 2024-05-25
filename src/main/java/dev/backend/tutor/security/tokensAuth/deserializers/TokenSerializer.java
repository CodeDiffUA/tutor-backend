package dev.backend.tutor.security.tokensAuth.deserializers;

import dev.backend.tutor.security.tokensAuth.tokens.Token;

public interface TokenSerializer {

    String serialize(Token accessToken);
}
