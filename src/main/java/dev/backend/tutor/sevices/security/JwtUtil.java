package dev.backend.tutor.sevices.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Duration jwtLifetime;

    @Value("${jwt.refresh.expiration}")
    private Duration refreshLifetime;

    public Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Date issuedAt(){
        return new Date(System.currentTimeMillis());
    }

    public Date expiration(){
        return new Date(System.currentTimeMillis()+jwtLifetime.toMillis());
    }
    public Long refreshLifeTime(){
        return refreshLifetime.toMillis();
    }

}
