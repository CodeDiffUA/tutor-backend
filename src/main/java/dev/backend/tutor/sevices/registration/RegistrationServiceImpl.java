package dev.backend.tutor.sevices.registration;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.dtos.auth.RegistrationDtoResponse;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.emails.ConfirmationEmailTokenRepository;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.registration.validation.StudentValidationService;
import dev.backend.tutor.sevices.security.refresh.TokenFactory;
import dev.backend.tutor.utills.student.StudentBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final StudentRepository studentRepository;
    private final StudentValidationService validationService;
    private final PasswordEncoder passwordEncoder;
    private final TokenFactory tokenFactory;
    private final ConfirmationEmailTokenRepository confirmationEmailTokenRepository;

    public RegistrationServiceImpl(StudentRepository studentRepository, StudentValidationService validationService, PasswordEncoder passwordEncoder, TokenFactory tokenFactory, ConfirmationEmailTokenRepository confirmationEmailTokenRepository) {
        this.studentRepository = studentRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
        this.tokenFactory = tokenFactory;
        this.confirmationEmailTokenRepository = confirmationEmailTokenRepository;
    }

    @Override
    @Transactional
    public RegistrationDtoResponse registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        validateRequest(registrationDtoRequest);
        Student student = createStudent(registrationDtoRequest);
        studentRepository.save(student);
        ConfirmationEmailToken token = createConfirmationToken(student);
        confirmationEmailTokenRepository.save(token);
        return new RegistrationDtoResponse(token.getToken());
    }

    private ConfirmationEmailToken createConfirmationToken(Student student) {
        var userDetails = new User(student.getUsername(), student.getPassword(), student.getRoles());
        return tokenFactory.createConfirmationEmailToken(userDetails);
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
