package dev.backend.tutor.security.tokensAuth.factories;

import dev.backend.tutor.security.tokensAuth.tokens.RefreshToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DefaultRefreshTokenFactory implements RefreshTokenFactory{

    private Duration tokenTtl = Duration.ofDays(14);

    @Override
    public RefreshToken createToken(Authentication authentication) {
        var authorities = new ArrayList<>(List.of("ROLE_JWT_REFRESH"));
        var createdAt = Instant.now();
        var expiresAt =  createdAt.plus(this.tokenTtl);
        return new RefreshToken(UUID.randomUUID(), authentication.getName(), authorities, createdAt, expiresAt);
    }

    public void setTokenTtl(Duration tokenTtl) {
        this.tokenTtl = tokenTtl;
    }
}
