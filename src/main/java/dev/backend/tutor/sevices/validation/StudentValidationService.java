package dev.backend.tutor.sevices.validation;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.friendship.AlreadyFriendsException;
import dev.backend.tutor.exceptions.friendship.BlockedUsersException;
import dev.backend.tutor.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentValidationService implements ValidationStudentDataService, ValidationStudentFriendshipService{

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

    @Transactional(readOnly = true)
    public void validateIfStudentsAreFriends(Student senderStudent, Student recipientStudent) throws AlreadyFriendsException {
        if (senderStudent.getFriends().contains(recipientStudent)) {
            throw new AlreadyFriendsException("You already have this student in friends");
        }
    }

    @Transactional(readOnly = true)
    public void validateIfSomeoneBlocked(Student senderStudent, Student recipientStudent) throws BlockedUsersException {
        if (senderStudent.getBlockedStudents().contains(recipientStudent)) {
            throw new BlockedUsersException("You have blocked this user");
        }
        if (recipientStudent.getBlockedStudents().contains(senderStudent)) {
            throw new BlockedUsersException("This user blocked you");
        }
    }
}
