package dev.backend.tutor.sevices.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtParser {

    private final JwtUtil jwtUtil;

    public JwtParser(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public List<String> extractRoles(String token){
        return extractAllClaims(token).get("roles", List.class);
    }
    private <T> T extractClaim(String token, Function<Claims, T> function) {
        var claims = extractAllClaims(token);
        return function.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtUtil.getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
