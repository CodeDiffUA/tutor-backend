package dev.backend.tutor.sevices.security;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.entities.auth.Token;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.security.jwt.JwtBuilder;
import dev.backend.tutor.sevices.security.jwt.JwtUtil;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenFactory {

    private final StudentRepository studentRepository;
    private final JwtUtil jwtUtil;
    private final JwtBuilder jwtBuilder;

    public TokenFactory(StudentRepository studentRepository, JwtUtil jwtUtil, JwtBuilder jwtBuilder) {
        this.studentRepository = studentRepository;
        this.jwtUtil = jwtUtil;
        this.jwtBuilder = jwtBuilder;
    }

    public String createJwt(Student student) {
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = student.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", roles);

        return jwtBuilder.buildJwt(claims, student.getUsername());
    }
    public RefreshToken createRefreshToken(@NonNull Student student) throws NotFoundUserException {
        return Token.builder()
                .withToken(UUID.randomUUID().toString())
                .withExpiryDate(Instant.now().plusMillis(jwtUtil.refreshLifeTime()))
                .withStudent(student)
                .buildRefreshToken();
    }

    public ConfirmationEmailToken createConfirmationEmailToken(@NonNull Student student) {
        return Token.builder()
                .withToken(UUID.randomUUID().toString())
                .withExpiryDate(Instant.now().plusSeconds(3600*24))
                .withStudent(student)
                .buildConfirmationToken();
    }
}
