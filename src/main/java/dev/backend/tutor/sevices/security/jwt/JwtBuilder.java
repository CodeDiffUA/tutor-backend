package dev.backend.tutor.sevices.security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtBuilder {

    private final JwtUtil jwtUtil;

    public JwtBuilder(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String generateJwt(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", roles);

        return buildJwt(claims, userDetails.getUsername());
    }

    private String buildJwt(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(jwtUtil.issuedAt())
                .expiration(jwtUtil.expiration())
                .signWith(jwtUtil.getSignKey())
                .compact();
    }
}
