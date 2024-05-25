package dev.backend.tutor.security.tokensAuth.factories;

import dev.backend.tutor.security.tokensAuth.tokens.AccessToken;
import dev.backend.tutor.security.tokensAuth.tokens.Token;
import dev.backend.tutor.entities.auth.Role;
import dev.backend.tutor.repositories.sql.student.StudentRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class DefaultAccessTokenFactory implements AccessTokenFactory{

    private Duration tokenTtl = Duration.ofMinutes(15);
    private final StudentRepository studentRepository;

    public DefaultAccessTokenFactory(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public AccessToken createToken(Token refreshToken) {
        var createdAt = Instant.now();
        var expiresAt =  createdAt.plus(this.tokenTtl);
        List<String> authorities = studentRepository.fetchUserRoles(refreshToken.subject());
        authorities.add(Role.ROLE_LOGOUT.name());
        return new AccessToken(UUID.randomUUID(), refreshToken.subject(), authorities, createdAt, expiresAt);
    }

    public void setTokenTtl(Duration tokenTtl) {
        this.tokenTtl = tokenTtl;
    }
}
