package dev.backend.tutor.config.security.tokensAuth.tokens;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public abstract class Token {
    protected final UUID id;
    protected final String subject;
    protected final List<String> authorities;
    protected final Instant createdAt;
    protected final Instant expiresAt;

    public Token(UUID id, String subject, List<String> authorities, Instant createdAt, Instant expiresAt) {
        this.id = id;
        this.subject = subject;
        this.authorities = authorities;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public UUID id() {
        return id;
    }

    public String subject() {
        return subject;
    }

    public List<String> authorities() {
        return authorities;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant expiresAt() {
        return expiresAt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", authorities=" + authorities +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                '}';
    }
}