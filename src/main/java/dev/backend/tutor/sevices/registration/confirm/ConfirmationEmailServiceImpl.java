package dev.backend.tutor.sevices.registration.confirm;

import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.repositories.emails.ConfirmationEmailTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ConfirmationEmailServiceImpl implements ConfirmationEmailService{

    private final ConfirmationEmailTokenRepository confirmationEmailTokenRepository;

    public ConfirmationEmailServiceImpl(ConfirmationEmailTokenRepository confirmationEmailTokenRepository) {
        this.confirmationEmailTokenRepository = confirmationEmailTokenRepository;
    }


    @Override
    @Transactional
    public void confirmEmail(String token) throws InvalidTokenException {
        var confirmationToken = confirmationEmailTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("token not found"));
        if (confirmationToken.getExpiryDate().isBefore(Instant.now())){
            throw new InvalidTokenException("token expired");
        }
        var student = confirmationToken.getStudent();
        student.setEnabled(true);
        confirmationEmailTokenRepository.delete(confirmationToken);
    }
}
