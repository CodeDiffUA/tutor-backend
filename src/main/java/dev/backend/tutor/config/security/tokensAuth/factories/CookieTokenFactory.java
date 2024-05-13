package dev.backend.tutor.config.security.tokensAuth.factories;

import dev.backend.tutor.config.security.tokensAuth.tokens.CookieToken;
import dev.backend.tutor.config.security.tokensAuth.tokens.RefreshToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CookieTokenFactory{

    private Duration tokenTtl = Duration.ofDays(14);

    public CookieToken createToken(Authentication authentication) {
        var authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();
        var createdAt = Instant.now();
        var expiresAt =  createdAt.plus(this.tokenTtl);
        return new CookieToken(UUID.randomUUID(), authentication.getName(), authorities, createdAt, expiresAt);
    }

    public void setTokenTtl(Duration tokenTtl) {
        this.tokenTtl = tokenTtl;
    }
}
