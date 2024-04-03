package dev.backend.tutor.sevices.forgotPassword;

import dev.backend.tutor.dtos.forgotPassword.ForgotPasswordDtoRequest;
import dev.backend.tutor.exceptions.EqualPasswordException;
import dev.backend.tutor.repositories.student.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void forgotPassword(ForgotPasswordDtoRequest forgotPasswordDtoRequest) throws EqualPasswordException {
        String newPass = forgotPasswordDtoRequest.password();
        var student = studentRepository.findStudentsByUsernameOrEmail(forgotPasswordDtoRequest.email()).orElseThrow();
        String oldPass = studentRepository.findPasswordByEmail(forgotPasswordDtoRequest.email())
                .orElseThrow();
        if (!passwordEncoder.matches(newPass, oldPass)){
            student.setPassword(passwordEncoder.encode(newPass));
            studentRepository.save(student);
        } else {
            throw new EqualPasswordException("same password");
        }
    }
}
