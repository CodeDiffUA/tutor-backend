package dev.backend.tutor.sevices.security.refresh;

import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.security.jwt.JwtUtil;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenFactory {

    private final StudentRepository studentRepository;
    private final JwtUtil jwtUtil;

    public RefreshTokenFactory(StudentRepository studentRepository, JwtUtil jwtUtil) {
        this.studentRepository = studentRepository;
        this.jwtUtil = jwtUtil;
    }

    public RefreshToken createRefreshToken(@NonNull UserDetails userDetails) throws NotFoundUserException {
        return RefreshToken.builder()
                .withToken(UUID.randomUUID().toString())
                .withExpiryDate(Instant.now().plusMillis(jwtUtil.refreshLifeTime()))
                .withStudent(studentRepository.findStudentByUsername(userDetails.getUsername())
                        .orElseThrow(() -> new NotFoundUserException("cannot find user - " + userDetails.getUsername())))
                .build();
    }
}
