package dev.backend.tutor.sevices.student;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.validation.StudentValidationService;
import dev.backend.tutor.sevices.validation.ValidationService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements RegistrationService {

    private final ValidationService validationService;
    private final StudentRepository studentRepository;
//    private final PasswordEncoder passwordEncoder;

    public StudentService(
            StudentRepository studentRepository,
            StudentValidationService validationService
//            PasswordEncoder passwordEncoder
            ) {
        this.studentRepository = studentRepository;
        this.validationService = validationService;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        validateRequest(registrationDtoRequest);
        Student student = buildStudent(registrationDtoRequest);
        System.out.println(student.getUsername());
        studentRepository.save(student);
    }

    private void validateRequest(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        validationService.validateEmail(registrationDtoRequest.email());
        validationService.validateUsername(registrationDtoRequest.username());
    }

    private Student buildStudent(RegistrationDtoRequest registrationDtoRequest){
        var password = registrationDtoRequest.password();
//        var encryptedPassword = passwordEncoder.encode(password);
        return Student.builder()
                .withUsername(registrationDtoRequest.username())
                .withEmail(registrationDtoRequest.email())
                .withPassword(password)
                .withAge(registrationDtoRequest.age())
                .withForm(registrationDtoRequest.form())
                .build();
    }
}
