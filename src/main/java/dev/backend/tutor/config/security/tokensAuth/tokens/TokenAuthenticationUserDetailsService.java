package dev.backend.tutor.config.security.tokensAuth.tokens;

import dev.backend.tutor.repositories.tokens.JwtTokensRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Stream;

@Service
public class TokenAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final JwtTokensRepository jwtTokensRepository;

    public TokenAuthenticationUserDetailsService(JwtTokensRepository jwtTokensRepository) {
        this.jwtTokensRepository = jwtTokensRepository;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticationToken)
            throws UsernameNotFoundException {
        if (authenticationToken.getPrincipal() instanceof Token token) {
            return new TokenUser(token.subject(), "nopassword",
                    checkIfEnabled(token),
                    checkIfExpired(token),
                    true,
                    true,
                    token.authorities().stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList(), token);
        }

        throw new UsernameNotFoundException("Principal must be of type Token");
    }

    private boolean checkIfEnabled(Token token) {
        return Stream.of("ROLE_BANNED", "ROLE_UNACTIVATED")
                .noneMatch(role -> token.authorities().contains(role)) && checkIfLoggedOut(token);

    }

    private boolean checkIfExpired(Token token) {
        return token.expiresAt().isAfter(Instant.now());
    }
    private boolean checkIfLoggedOut(Token token) {
        return !jwtTokensRepository.checkIfTokenDeactivated(token.id());
    }
}
