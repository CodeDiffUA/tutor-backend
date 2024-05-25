package dev.backend.tutor.security.tokensAuth.tokens;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CookieToken extends Token {
    public CookieToken(UUID id, String subject, List<String> authorities, Instant createdAt, Instant expiresAt) {
        super(id, subject, authorities, createdAt, expiresAt);
    }
}