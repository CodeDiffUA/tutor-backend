package dev.backend.tutor.sevices.validation;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.AlreadyFriendsException;
import dev.backend.tutor.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentValidationService implements ValidationService{

    private final StudentRepository studentRepository;

    public StudentValidationService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void validateEmail(String email) throws AlreadyExistsUserException {
        boolean emailAlreadyExists = studentRepository.existsStudentByEmail(email);
        if (emailAlreadyExists) {
            throw new AlreadyExistsUserException("This email already exists");
        }
    }

    @Override
    public void validateUsername(String username) throws AlreadyExistsUserException {
        boolean usernameAlreadyExists = studentRepository.existsStudentByUsername(username);
        if (usernameAlreadyExists) {
            throw new AlreadyExistsUserException("This username already exists");
        }
    }

    public void validateIfStudentsAreFriends(Student senderStudent, Student recipientStudent) throws AlreadyFriendsException {
        if (senderStudent.getFriends().contains(recipientStudent)) {
            throw new AlreadyFriendsException("You already have this student in friends");
        }
    }

    public void validateIfSomeoneBlocked(Student senderStudent, Student recipientStudent) throws AlreadyFriendsException {
        if (senderStudent.getBlockedStudents().contains(recipientStudent)) {
            throw new AlreadyFriendsException("You have blocked this user");
        }
        if (recipientStudent.getBlockedStudents().contains(senderStudent)) {
            throw new AlreadyFriendsException("This user blocked you");
        }
    }
}
