package dev.backend.tutor.security.tokensAuth.serializers;

import dev.backend.tutor.security.tokensAuth.tokens.Token;

public interface TokenDeserializer {

    Token deserialize(String token);
}
