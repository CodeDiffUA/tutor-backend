package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.friendship.RequestFriendshipRequestDto;
import dev.backend.tutor.dtos.message.MessageDto;
import dev.backend.tutor.entities.messegeEntities.Notification;
import dev.backend.tutor.exceptions.friendship.AlreadyFriendsException;
import dev.backend.tutor.exceptions.friendship.BlockedUsersException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.friendship.request.FriendshipRequestServiceImpl;
import dev.backend.tutor.sevices.nofications.NotificationService;
import dev.backend.tutor.sevices.validation.StudentValidationService;
import dev.backend.tutor.utills.student.DateUtil;
import dev.backend.tutor.utils.StudentGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FriendshipRequestServiceImplTest {

    @InjectMocks
    private FriendshipRequestServiceImpl friendshipRequestService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentValidationService studentValidationService;

    @Mock
    private NotificationService notificationService;

    @Test
    public void Should_SuccessfullySendRequest() throws FriendshipException, NotFoundUserException {
        // arrange
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        RequestFriendshipRequestDto requestDto = new RequestFriendshipRequestDto(senderLogin, recipientLogin);

        var senderStudent = StudentGenerator.generateStudent(senderLogin);
        var recipientStudent = StudentGenerator.generateStudent(recipientLogin);
        var expectedStudents = List.of(senderStudent, recipientStudent);

        when(studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                senderLogin, recipientLogin
        )).thenReturn(expectedStudents);

        doNothing().when(studentValidationService).validateIfStudentsAreFriends(senderStudent, recipientStudent);
        doNothing().when(studentValidationService).validateIfSomeoneBlocked(senderStudent, recipientStudent);

        // when
        friendshipRequestService.requestFriendShip(requestDto);

        // verify
        verify(notificationService).notifyUser(any(Notification.class));
    }
    @Test
    public void Should_NotSendRequestMessage_When_AreFriends() throws AlreadyFriendsException {
        // arrange
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        RequestFriendshipRequestDto requestDto = new RequestFriendshipRequestDto(senderLogin, recipientLogin);

        var senderStudent = StudentGenerator.generateStudent(senderLogin);
        var recipientStudent = StudentGenerator.generateStudent(recipientLogin);
        var expectedStudents = List.of(senderStudent, recipientStudent);

        when(studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                senderLogin, recipientLogin
        )).thenReturn(expectedStudents);

        doThrow(AlreadyFriendsException.class).when(studentValidationService).validateIfStudentsAreFriends(senderStudent, recipientStudent);
        // when/then
        Assertions.assertThrows(AlreadyFriendsException.class,
                () ->friendshipRequestService.requestFriendShip(requestDto));

        // verify
        verify(notificationService, never()).notifyUser(any(Notification.class));
    }

    @Test
    public void Should_NotSendRequestMessage_When_SomeoneBlocked() throws AlreadyFriendsException, BlockedUsersException {
        // arrange
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        RequestFriendshipRequestDto requestDto = new RequestFriendshipRequestDto(senderLogin, recipientLogin);

        var senderStudent = StudentGenerator.generateStudent(senderLogin);
        var recipientStudent = StudentGenerator.generateStudent(recipientLogin);
        var expectedStudents = List.of(senderStudent, recipientStudent);

        when(studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                senderLogin, recipientLogin
        )).thenReturn(expectedStudents);

        doNothing().when(studentValidationService).validateIfStudentsAreFriends(senderStudent, recipientStudent);
        doThrow(BlockedUsersException.class).when(studentValidationService).validateIfSomeoneBlocked(senderStudent, recipientStudent);
        // when/then
        Assertions.assertThrows(BlockedUsersException.class,
                () ->friendshipRequestService.requestFriendShip(requestDto));

        // verify
        verify(notificationService, never()).notifyUser(any(Notification.class));
    }

    @Test
    void Should_ThrowNotFoundUserException_When_UsernameInvalid() {
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        RequestFriendshipRequestDto requestDto = new RequestFriendshipRequestDto(senderLogin, recipientLogin);
        when(studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                senderLogin, recipientLogin
        )).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NotFoundUserException.class,
                () -> friendshipRequestService.requestFriendShip(requestDto));
        verify(notificationService, never()).notifyUser(any(Notification.class));
    }
}