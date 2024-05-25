package dev.backend.tutor.services.security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtBuilder {

    private final JwtUtil jwtUtil;

    public JwtBuilder(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    public String buildJwt(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(jwtUtil.issuedAt())
                .expiration(jwtUtil.expiration())
                .signWith(jwtUtil.getSignKey())
                .compact();
    }
}
