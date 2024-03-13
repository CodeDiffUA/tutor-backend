package dev.backend.tutor.sevices.student;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.student.RegistrationService;
import dev.backend.tutor.sevices.validation.ValidationService;
import dev.backend.tutor.utills.student.StudentBuilder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final StudentRepository studentRepository;
    private final ValidationService validationService;

    public RegistrationServiceImpl(StudentRepository studentRepository, ValidationService validationService) {
        this.studentRepository = studentRepository;
        this.validationService = validationService;
    }

    @Override
    public void registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        validateRequest(registrationDtoRequest);
        Student student = StudentBuilder.buildStudent(registrationDtoRequest);
        studentRepository.save(student);
    }

    private void validateRequest(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        validationService.validateEmail(registrationDtoRequest.email());
        validationService.validateUsername(registrationDtoRequest.username());
    }
}
