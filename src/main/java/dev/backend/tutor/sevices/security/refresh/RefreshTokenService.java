package dev.backend.tutor.sevices.security.refresh;

import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.refresh.RefreshTokenRepository;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.security.jwt.JwtUtil;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final StudentRepository studentRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil, StudentRepository studentRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
        this.studentRepository = studentRepository;
    }

    public RefreshToken findRefreshTokenByToken(String token) throws InvalidTokenException {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);
    }

    public RefreshToken createRefreshToken(@NonNull UserDetails userDetails) throws NotFoundUserException {
        var refreshToken = RefreshToken.builder()
                .withToken(UUID.randomUUID().toString())
                .withExpiryDate(Instant.now().plusMillis(jwtUtil.refreshLifeTime()))
                .withStudent(studentRepository.findStudentByUsername(userDetails.getUsername())
                        .orElseThrow(() -> new NotFoundUserException("cannot find user - " + userDetails.getUsername())))
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

}
