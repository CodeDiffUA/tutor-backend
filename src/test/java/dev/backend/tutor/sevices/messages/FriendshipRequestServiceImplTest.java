package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.FriendShipRequestDto;
import dev.backend.tutor.dtos.MessageDto;
import dev.backend.tutor.exceptions.frienship.AlreadyFriendsException;
import dev.backend.tutor.exceptions.frienship.BlockedUsersException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.frienship.FriendshipException;
import dev.backend.tutor.repositories.StudentRepository;
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
    private MessageService messageService;

    @Test
    public void Should_SuccessfullySendRequest() throws FriendshipException, NotFoundUserException {
        // arrange
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        FriendShipRequestDto requestDto = new FriendShipRequestDto(senderLogin, recipientLogin);

        var senderStudent = StudentGenerator.generateStudent(senderLogin);
        var recipientStudent = StudentGenerator.generateStudent(recipientLogin);
        var expectedStudents = List.of(senderStudent, recipientStudent);

        when(studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                senderLogin, recipientLogin
        )).thenReturn(expectedStudents);

        doNothing().when(studentValidationService).validateIfStudentsAreFriends(senderStudent, recipientStudent);
        doNothing().when(studentValidationService).validateIfSomeoneBlocked(senderStudent, recipientStudent);
        var expectedMessage = new MessageDto(senderLogin, recipientLogin, recipientLogin + ", user " + senderLogin + " wants to become your friend", DateUtil.currentTimeStamp());
        when(messageService.messageDtoForFriendshipRequest(senderLogin, recipientLogin)).thenReturn(expectedMessage);

        // when
        friendshipRequestService.requestFriendShip(requestDto);

        // verify
        verify(messageService).sendMessageToUser(recipientLogin, expectedMessage);
    }
    @Test
    public void Should_NotSendRequestMessage_When_AreFriends() throws AlreadyFriendsException {
        // arrange
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        FriendShipRequestDto requestDto = new FriendShipRequestDto(senderLogin, recipientLogin);

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
        verify(messageService, never()).sendMessageToUser(recipientLogin, expectedMessage);
    }

    @Test
    public void Should_NotSendRequestMessage_When_SomeoneBlocked() throws AlreadyFriendsException, BlockedUsersException {
        // arrange
        var senderLogin = "senderLogin";
        var recipientLogin = "senderLogin";
        FriendShipRequestDto requestDto = new FriendShipRequestDto(senderLogin, recipientLogin);

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
        verify(messageService, never()).sendMessageToUser(recipientLogin, expectedMessage);
    }
}