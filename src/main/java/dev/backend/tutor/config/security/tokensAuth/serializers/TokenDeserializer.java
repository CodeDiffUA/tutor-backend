package dev.backend.tutor.config.security.tokensAuth.serializers;

import dev.backend.tutor.config.security.tokensAuth.tokens.Token;

public interface TokenDeserializer {

    Token deserialize(String token);
}
