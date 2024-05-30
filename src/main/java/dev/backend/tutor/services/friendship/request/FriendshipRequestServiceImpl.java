package dev.backend.tutor.services.friendship.request;

import dev.backend.tutor.dtos.friendship.RequestFriendshipRequestDto;
import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;
import dev.backend.tutor.repositories.sql.student.StudentRepository;
import dev.backend.tutor.services.auth.signUp.validation.StudentValidationService;
import dev.backend.tutor.services.nofications.NotificationService;
import dev.backend.tutor.utills.student.NotificationFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.backend.tutor.utills.student.StudentListProcessor.extractStudentFromListByUsername;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService{

    private final NotificationService notificationService;
    private final StudentRepository studentRepository;
    private final StudentValidationService studentValidationService;

    public FriendshipRequestServiceImpl(NotificationService notificationService, StudentRepository studentRepository, StudentValidationService studentValidationService) {
        this.notificationService = notificationService;
        this.studentRepository = studentRepository;
        this.studentValidationService = studentValidationService;
    }

    private record SenderAndRecipient(Student sender, Student recipient){}

    @Override
    public void requestFriendShip(RequestFriendshipRequestDto friendShipRequestDto)
            throws NotFoundUserException, FriendshipException {
        var senderLogin = friendShipRequestDto.sender();
        var recipientLogin = friendShipRequestDto.recipient();
        SenderAndRecipient students = validateIfTheyCanBecomeFriends(senderLogin, recipientLogin);
        sendRequestToRecipient(students);
    }

    private void sendRequestToRecipient(SenderAndRecipient students) {
        var notification = NotificationFactory.requestNotification(students.sender, students.recipient);
        notificationService.notifyUser(notification);
    }

    private SenderAndRecipient validateIfTheyCanBecomeFriends(String senderLogin, String recipientLogin)
            throws FriendshipException, NotFoundUserException {
        List<Student> studentThatAboutToBeFriends =  studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(senderLogin,recipientLogin);
        var senderStudent = extractStudentFromListByUsername(studentThatAboutToBeFriends, senderLogin);
        var recipientStudent = extractStudentFromListByUsername(studentThatAboutToBeFriends, recipientLogin);
        studentValidationService.validateIfStudentsAreFriends(senderStudent, recipientStudent);
        studentValidationService.validateIfSomeoneBlocked(senderStudent, recipientStudent);
        return new SenderAndRecipient(senderStudent, recipientStudent);
    }


}
