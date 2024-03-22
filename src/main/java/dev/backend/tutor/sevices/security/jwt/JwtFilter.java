package dev.backend.tutor.sevices.security.jwt;

import dev.backend.tutor.exceptions.InvalidJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    private final JwtParser jwtParser;

    public JwtFilter(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    private record JwtAndUsername(String jwt, String username){}
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        JwtAndUsername jwtAndUsername = parseJwtAndUsernameFromAuthHeader(authHeader);

        String jwt = jwtAndUsername.jwt();
        String username = jwtAndUsername.username();

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            addUsersAuthTokenToContext(jwt, username);
        }
        filterChain.doFilter(request, response);
    }

    private void addUsersAuthTokenToContext(String jwt, String username) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                null,
                jwtParser.extractRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList());
        SecurityContextHolder.getContext().setAuthentication(token);}

    private JwtAndUsername parseJwtAndUsernameFromAuthHeader(String authHeader) throws InvalidJwtException {
        if (authHeader!= null && authHeader.startsWith(BEARER)){
            String jwt = authHeader.substring(7);
            validateJwtExpiration(jwt);
            String username = parseUsernameFromJwt(jwt);
            return new JwtAndUsername(jwt, username);
        }
        return new JwtAndUsername(null, null);
    }

    private String parseUsernameFromJwt(String jwt) {
        return jwtParser.extractUsername(jwt);
    }

    private void validateJwtExpiration(String jwt) throws InvalidJwtException {
        if (jwtParser.extractExpiration(jwt).before(new Date())) {
            throw new InvalidJwtException("expired access token");
        }
    }

}
