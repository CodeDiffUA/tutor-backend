package dev.backend.tutor.sevices.friendship.response;

import dev.backend.tutor.dtos.friendship.RequestFriendshipResponseDto;
import dev.backend.tutor.entities.messegeEntities.Notification;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.nofications.NotificationService;
import dev.backend.tutor.utills.student.NotificationFactory;
import static dev.backend.tutor.utills.student.StudentListProcessor.extractStudentFromListByUsername;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FriendshipResponseServiceImpl implements FriendshipResponseService {

    private final StudentRepository studentRepository;
    private final NotificationService notificationService;

    public FriendshipResponseServiceImpl(StudentRepository studentRepository, NotificationService notificationService) {
        this.studentRepository = studentRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public void responseFriendship(RequestFriendshipResponseDto friendshipResponseDto) throws NotFoundUserException {
        Notification notification;
        if (!friendshipResponseDto.acceptedFriendship()) {
            notification = createDeclineNotificationForRecipient(friendshipResponseDto.recipient());
        } else {
            notification = establishFriendshipAndCreateAcceptNotification(friendshipResponseDto.sender(), friendshipResponseDto.recipient());
        }
        notificationService.notifyUser(notification);
    }

    private Notification createDeclineNotificationForRecipient(String username) throws NotFoundUserException {
        Notification notification;
        var recipient = studentRepository.findStudentByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("cannot find user " + username));
        notification = NotificationFactory.declineRequestNotification(recipient);
        return notification;
    }

    private Notification establishFriendshipAndCreateAcceptNotification(String senderUsername, String recipientUsername) throws NotFoundUserException {
        var fetchedStudents = studentRepository.findStudentsByUsernameFetchFriends(senderUsername, recipientUsername);
        var sender = extractStudentFromListByUsername(fetchedStudents, senderUsername);
        var recipient = extractStudentFromListByUsername(fetchedStudents, recipientUsername);
        sender.addFriend(recipient);
        return NotificationFactory.acceptRequestNotification(recipient);
    }
}
