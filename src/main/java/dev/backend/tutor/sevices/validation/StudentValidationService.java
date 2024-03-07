package dev.backend.tutor.sevices.validation;

import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentValidationService implements ValidationService{

    private final StudentRepository studentRepository;

    public StudentValidationService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void validateEmail(String email) {
        boolean emailAlreadyExists = studentRepository.existsStudentByEmail(email);
        if (emailAlreadyExists) {
            throw new AlreadyExistsUserException("This email already exists");
        }
    }

    @Override
    public void validateUsername(String username) {
        boolean usernameAlreadyExists = studentRepository.existsStudentByUsername(username);
        if (usernameAlreadyExists) {
            throw new AlreadyExistsUserException("This username already exists");
        }
    }
}
