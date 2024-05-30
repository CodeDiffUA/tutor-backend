package dev.backend.tutor.services.auth.signUp.confirm;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.sql.emails.ConfirmationEmailTokenRepository;
import dev.backend.tutor.services.security.TokenFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ConfirmationEmailServiceImpl implements ConfirmationEmailService{

    private final ConfirmationEmailTokenRepository confirmationEmailTokenRepository;
    private final TokenFactory tokenFactory;

    public ConfirmationEmailServiceImpl(ConfirmationEmailTokenRepository confirmationEmailTokenRepository, TokenFactory tokenFactory) {
        this.confirmationEmailTokenRepository = confirmationEmailTokenRepository;
        this.tokenFactory = tokenFactory;
    }

    @Override
    @Transactional
    public void confirmEmail(String token) throws InvalidTokenException {
        confirmStudent(token);
    }

    @Transactional(rollbackFor = Exception.class)
    public JwtAndRefreshDto confirmEmailAndLogin(String token) throws InvalidTokenException, NotFoundUserException {
        var student = confirmStudent(token);
        var jwt = tokenFactory.createJwt(student);
        var refreshToken = tokenFactory.createRefreshToken(student);
        return new JwtAndRefreshDto(jwt, refreshToken.getToken());
    }

    private Student confirmStudent(String token) throws InvalidTokenException {
        var confirmationToken = confirmationEmailTokenRepository.findByTokenWithStudentWithRoles(token)
                .orElseThrow(() -> new InvalidTokenException("token not found"));
        if (confirmationToken.getExpiryDate().isBefore(Instant.now())){
            throw new InvalidTokenException("token expired");
        }
        var student = confirmationToken.getStudent();
        student.activate();
        confirmationEmailTokenRepository.delete(confirmationToken);
        return student;
    }

}
