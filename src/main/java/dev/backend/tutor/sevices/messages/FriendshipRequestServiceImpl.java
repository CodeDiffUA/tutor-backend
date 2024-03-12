package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.ExceptionDto;
import dev.backend.tutor.dtos.FriendShipRequestDto;
import dev.backend.tutor.dtos.MessageDto;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.AlreadyFriendsException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.validation.StudentValidationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService{

    private final MessageService messageService;
    private final StudentRepository studentRepository;
    private final StudentValidationService studentValidationService;

    public FriendshipRequestServiceImpl(
            MessageService messageService,
            StudentRepository studentRepository,
            StudentValidationService studentValidationService) {
        this.messageService = messageService;
        this.studentRepository = studentRepository;
        this.studentValidationService = studentValidationService;
    }

    @Override
    public void requestFriendShip(FriendShipRequestDto friendShipRequestDto)
            throws NotFoundUserException, AlreadyFriendsException {
        var senderLogin = friendShipRequestDto.sender();
        var recipientLogin = friendShipRequestDto.recipient();
        validateIfTheyCanBecomeFriends(senderLogin, recipientLogin);
        MessageDto messageDtoForFriendShipRequest = getMessageDtoForFriendShipRequest(senderLogin,recipientLogin);
        sendRequestToRecipient(recipientLogin, messageDtoForFriendShipRequest);
    }

    private MessageDto getMessageDtoForFriendShipRequest(String senderLogin, String recipientLogin) {
        return messageService.messageDtoForFriendshipRequest(senderLogin, recipientLogin);
    }

    private void sendRequestToRecipient(
            String recipientLogin, MessageDto messageDto) {
        messageService.sendMessageToUser(recipientLogin, messageDto);
    }

    private void sendExceptionToSender(
            String senderLogin, String exceptionMessage) {
        ExceptionDto exceptionDto = messageService.exceptionDtoForFriendshipRequest(exceptionMessage);
        messageService.sendExceptionToUser(senderLogin, exceptionDto);
    }


    private void validateIfTheyCanBecomeFriends(String senderLogin, String recipientLogin)
            throws AlreadyFriendsException, NotFoundUserException {
        List<Student> studentThatAboutToBeFriends =  studentRepository
                .findSenderAndRecipientStudentsWithFriendsAndBlocked(senderLogin,recipientLogin);
        var senderStudent = extractStudentFromListByUsername(studentThatAboutToBeFriends, senderLogin, senderLogin);
        var recipientStudent = extractStudentFromListByUsername(studentThatAboutToBeFriends, recipientLogin, senderLogin);
        studentValidationService.validateIfStudentsAreFriends(senderStudent, recipientStudent);
        studentValidationService.validateIfSomeoneBlocked(senderStudent, recipientStudent);
    }

    private Student extractStudentFromListByUsername(List<Student> students, String username, String senderLogin) throws NotFoundUserException {
        return students.stream()
                .filter(student -> student.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> {
                    var message = "cannot find user - " + username;
                    sendExceptionToSender(senderLogin, message);
                    return new NotFoundUserException(message);
                });
    }

}
