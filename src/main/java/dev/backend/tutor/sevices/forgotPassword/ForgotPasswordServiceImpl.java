package dev.backend.tutor.sevices.forgotPassword;

import dev.backend.tutor.dtos.forgotPassword.ForgotPasswordDtoRequest;
import dev.backend.tutor.exceptions.EqualPasswordException;
import dev.backend.tutor.repositories.passwords.ConfirmationPasswordTokenRepository;
import dev.backend.tutor.repositories.student.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationPasswordTokenRepository confirmationPasswordTokenRepository;


    public ForgotPasswordServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder, ConfirmationPasswordTokenRepository confirmationPasswordTokenRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationPasswordTokenRepository = confirmationPasswordTokenRepository;
    }


    @Transactional
    public void forgotPassword(ForgotPasswordDtoRequest forgotPasswordDtoRequest) throws EqualPasswordException {
        String newPass = forgotPasswordDtoRequest.password();
        var student = confirmationPasswordTokenRepository
                .findStudentByConfirmationPasswordToken(forgotPasswordDtoRequest.confirmationPasswordToken());
        String oldPass = student.getPassword();
        if (!passwordEncoder.matches(newPass, oldPass)){
            student.setPassword(passwordEncoder.encode(newPass));
            studentRepository.save(student);
        } else {
            throw new EqualPasswordException("same password");
        }
    }
}
