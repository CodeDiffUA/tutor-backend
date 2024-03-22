package dev.backend.tutor.sevices.registration;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.registration.validation.StudentValidationService;
import dev.backend.tutor.utills.student.StudentBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final StudentRepository studentRepository;
    private final StudentValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(StudentRepository studentRepository, StudentValidationService validationService, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        validateRequest(registrationDtoRequest);
        Student student = StudentBuilder.buildStudent(registrationDtoRequest);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
    }

    private void validateRequest(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        validationService.validateEmail(registrationDtoRequest.email());
        validationService.validateUsername(registrationDtoRequest.username());
    }
}
