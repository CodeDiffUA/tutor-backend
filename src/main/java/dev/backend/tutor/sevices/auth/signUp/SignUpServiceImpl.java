package dev.backend.tutor.sevices.auth.signUp;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.auth.signUp.confirm.ConfirmationEmailServiceImpl;
import dev.backend.tutor.sevices.auth.signUp.validation.StudentValidationService;
import dev.backend.tutor.sevices.security.TokenFactory;
import dev.backend.tutor.sevices.student.StudentServiceImpl;
import dev.backend.tutor.utills.student.StudentBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpServiceImpl implements SignUpService {
    private final StudentServiceImpl studentService;
    private final StudentValidationService validationService;
    private final PasswordEncoder passwordEncoder;
    private final TokenFactory tokenFactory;
    private final ConfirmationEmailServiceImpl confirmationEmailService;
    private final EntityManager entityManager;

    public SignUpServiceImpl(StudentServiceImpl studentService, StudentValidationService validationService, PasswordEncoder passwordEncoder, TokenFactory tokenFactory, ConfirmationEmailServiceImpl confirmationEmailService, EntityManager entityManager) {
        this.studentService = studentService;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
        this.tokenFactory = tokenFactory;
        this.confirmationEmailService = confirmationEmailService;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException, NotFoundUserException {
        validateRequest(registrationDtoRequest);
        Student student = createStudent(registrationDtoRequest);
        studentService.saveStudent(student);
        entityManager.flush();
        ConfirmationEmailToken token = createConfirmationToken(student);
        confirmationEmailService.saveConfirmationToken(token);
//        return getJwtAndRefreshToken(student);
    }

//    private JwtAndRefreshDto getJwtAndRefreshToken(Student  student) throws NotFoundUserException {
//        String jwt = tokenFactory.createJwt(student);
//        RefreshToken refreshToken = tokenFactory.createRefreshToken(student);
//        return new JwtAndRefreshDto(jwt, refreshToken.getToken());
//    }

    private ConfirmationEmailToken createConfirmationToken(Student student) {
        return tokenFactory.createConfirmationEmailToken(student);
    }

    private Student createStudent(RegistrationDtoRequest registrationDtoRequest) {
        Student student = StudentBuilder.buildStudent(registrationDtoRequest);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return student;
    }

    private void validateRequest(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        validationService.validateEmail(registrationDtoRequest.email());
        validationService.validateUsername(registrationDtoRequest.username());
    }
}
