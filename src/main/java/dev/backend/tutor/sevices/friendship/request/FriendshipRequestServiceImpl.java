package dev.backend.tutor.sevices.friendship.request;

import dev.backend.tutor.dtos.ExceptionDto;
import dev.backend.tutor.dtos.messages.FriendshipRequestDto;
import dev.backend.tutor.dtos.messages.MessageDto;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.messages.MessageProvider;
import dev.backend.tutor.sevices.messages.MessageSender;
import dev.backend.tutor.sevices.messages.MessageService;
import dev.backend.tutor.sevices.validation.StudentValidationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService{

    private final MessageSender messageSender;
    private final StudentRepository studentRepository;
    private final StudentValidationService studentValidationService;

    public FriendshipRequestServiceImpl(MessageSender messageSender, StudentRepository studentRepository, StudentValidationService studentValidationService) {
        this.messageSender = messageSender;
        this.studentRepository = studentRepository;
        this.studentValidationService = studentValidationService;
    }

    @Override
    public void requestFriendShip(FriendshipRequestDto friendShipRequestDto)
            throws NotFoundUserException, FriendshipException {
        var senderLogin = friendShipRequestDto.sender();
        var recipientLogin = friendShipRequestDto.recipient();
        validateIfTheyCanBecomeFriends(senderLogin, recipientLogin);
        MessageDto messageDtoForFriendShipRequest = MessageProvider.messageDtoForFriendshipRequest(senderLogin,recipientLogin);
        sendRequestToRecipient(recipientLogin, messageDtoForFriendShipRequest);
    }

    private void sendRequestToRecipient(String recipientLogin, MessageDto messageDto) {
        messageSender.sendMessageToUser(recipientLogin, messageDto);
    }

    private void sendExceptionToSender(
            String senderLogin, String exceptionMessage) {
        ExceptionDto exceptionDto = MessageProvider.exceptionDtoForFriendshipRequest(exceptionMessage);
        messageSender.sendExceptionToUser(senderLogin, exceptionDto);
    }


    private void validateIfTheyCanBecomeFriends(String senderLogin, String recipientLogin)
            throws FriendshipException, NotFoundUserException {
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
