package dev.backend.tutor.sevices.auth.signUp;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
<<<<<<< Updated upstream
import dev.backend.tutor.entities.auth.RefreshToken;
=======
import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
>>>>>>> Stashed changes
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.auth.signUp.validation.StudentValidationService;
import dev.backend.tutor.sevices.security.TokenFactory;
import dev.backend.tutor.sevices.student.StudentServiceImpl;
import dev.backend.tutor.utills.student.StudentBuilder;
<<<<<<< Updated upstream
=======
import jakarta.persistence.EntityManager;
>>>>>>> Stashed changes
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpServiceImpl implements SignUpService {
    private final StudentServiceImpl studentService;
    private final StudentValidationService validationService;
    private final PasswordEncoder passwordEncoder;
    private final TokenFactory tokenFactory;

    public SignUpServiceImpl(StudentServiceImpl studentService, StudentValidationService validationService, PasswordEncoder passwordEncoder, TokenFactory tokenFactory) {
        this.studentService = studentService;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
        this.tokenFactory = tokenFactory;
    }

    @Override
    @Transactional
    public void registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException, NotFoundUserException {
        validateRequest(registrationDtoRequest);
        Student student = createStudent(registrationDtoRequest);
        studentService.saveStudent(student);
//        return getJwtAndRefreshToken(student);
    }


    @Transactional
    @Override
    public JwtAndRefreshDto registerAccountWithLogin(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException, NotFoundUserException {
        validateRequest(registrationDtoRequest);
        Student student = createStudent(registrationDtoRequest);
        studentService.saveStudent(student);
        return getJwtAndRefreshToken(student);
    }

    private JwtAndRefreshDto getJwtAndRefreshToken(Student  student) throws NotFoundUserException {
        String jwt = tokenFactory.createJwt(student);
        RefreshToken refreshToken = tokenFactory.createRefreshToken(student);
        return new JwtAndRefreshDto(jwt, refreshToken.getToken());
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
