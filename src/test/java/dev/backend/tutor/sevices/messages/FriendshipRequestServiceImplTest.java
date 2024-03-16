package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.messages.FriendshipRequestDto;
import dev.backend.tutor.dtos.messages.MessageDto;
import dev.backend.tutor.exceptions.friendship.AlreadyFriendsException;
import dev.backend.tutor.exceptions.friendship.BlockedUsersException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.friendship.request.FriendshipRequestServiceImpl;
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
    private MessageSender messageSender;

    @Test
    public void Should_SuccessfullySendRequest() throws FriendshipException, NotFoundUserException {
        // arrange
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        FriendshipRequestDto requestDto = new FriendshipRequestDto(senderLogin, recipientLogin);

        var senderStudent = StudentGenerator.generateStudent(senderLogin);
        var recipientStudent = StudentGenerator.generateStudent(recipientLogin);
        var expectedStudents = List.of(senderStudent, recipientStudent);

        when(studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                senderLogin, recipientLogin
        )).thenReturn(expectedStudents);

        doNothing().when(studentValidationService).validateIfStudentsAreFriends(senderStudent, recipientStudent);
        doNothing().when(studentValidationService).validateIfSomeoneBlocked(senderStudent, recipientStudent);
        var expectedMessage = new MessageDto(senderLogin, recipientLogin, recipientLogin + ", user " + senderLogin + " wants to become your friend", DateUtil.currentTimeStamp());

        // when
        friendshipRequestService.requestFriendShip(requestDto);

        // verify
        verify(messageSender).sendMessageToUser(recipientLogin, expectedMessage);
    }
    @Test
    public void Should_NotSendRequestMessage_When_AreFriends() throws AlreadyFriendsException {
        // arrange
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        FriendshipRequestDto requestDto = new FriendshipRequestDto(senderLogin, recipientLogin);

        var senderStudent = StudentGenerator.generateStudent(senderLogin);
        var recipientStudent = StudentGenerator.generateStudent(recipientLogin);
        var expectedStudents = List.of(senderStudent, recipientStudent);

        when(studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                senderLogin, recipientLogin
        )).thenReturn(expectedStudents);

        doThrow(AlreadyFriendsException.class).when(studentValidationService).validateIfStudentsAreFriends(senderStudent, recipientStudent);
        var expectedMessage = new MessageDto(senderLogin, recipientLogin, recipientLogin + ", user " + senderLogin + " wants to become your friend", DateUtil.currentTimeStamp());
        // when/then
        Assertions.assertThrows(AlreadyFriendsException.class,
                () ->friendshipRequestService.requestFriendShip(requestDto));

        // verify
        verify(messageSender, never()).sendMessageToUser(recipientLogin, expectedMessage);
    }

    @Test
    public void Should_NotSendRequestMessage_When_SomeoneBlocked() throws AlreadyFriendsException, BlockedUsersException {
        // arrange
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        FriendshipRequestDto requestDto = new FriendshipRequestDto(senderLogin, recipientLogin);

        var senderStudent = StudentGenerator.generateStudent(senderLogin);
        var recipientStudent = StudentGenerator.generateStudent(recipientLogin);
        var expectedStudents = List.of(senderStudent, recipientStudent);

        when(studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                senderLogin, recipientLogin
        )).thenReturn(expectedStudents);

        doNothing().when(studentValidationService).validateIfStudentsAreFriends(senderStudent, recipientStudent);
        doThrow(BlockedUsersException.class).when(studentValidationService).validateIfSomeoneBlocked(senderStudent, recipientStudent);
        var expectedMessage = new MessageDto(senderLogin, recipientLogin, recipientLogin + ", user " + senderLogin + " wants to become your friend", DateUtil.currentTimeStamp());
        // when/then
        Assertions.assertThrows(BlockedUsersException.class,
                () ->friendshipRequestService.requestFriendShip(requestDto));

        // verify
        verify(messageSender, never()).sendMessageToUser(recipientLogin, expectedMessage);
    }

    @Test
    void Should_ThrowNotFoundUserException_When_UsernameInvalid() {
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        FriendshipRequestDto requestDto = new FriendshipRequestDto(senderLogin, recipientLogin);
        var expectedMessage = new MessageDto(senderLogin, recipientLogin, recipientLogin + ", user " + senderLogin + " wants to become your friend", DateUtil.currentTimeStamp());
        when(studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                senderLogin, recipientLogin
        )).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NotFoundUserException.class,
                () -> friendshipRequestService.requestFriendShip(requestDto));
        verify(messageSender, never()).sendMessageToUser(recipientLogin, expectedMessage);
    }
}