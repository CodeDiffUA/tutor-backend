package dev.backend.tutor.security.userDetails;

import dev.backend.tutor.security.tokensAuth.tokens.Token;
import dev.backend.tutor.repositories.sql.student.StudentRepository;
import dev.backend.tutor.repositories.sql.tokens.JwtTokensRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final JwtTokensRepository jwtTokensRepository;
    private final StudentRepository studentRepository;

    public TokenAuthenticationUserDetailsService(JwtTokensRepository jwtTokensRepository, StudentRepository studentRepository) {
        this.jwtTokensRepository = jwtTokensRepository;
        this.studentRepository = studentRepository;
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
        return studentRepository.fetchUserRoles(token.subject()).stream()
                .noneMatch(role -> token.authorities().contains(role)) && checkIfLoggedOut(token);
    }

    private boolean checkIfExpired(Token token) {
        return token.expiresAt().isAfter(Instant.now());
    }
    private boolean checkIfLoggedOut(Token token) {
        return !jwtTokensRepository.checkIfTokenDeactivated(token.id());
    }
}
