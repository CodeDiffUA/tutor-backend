package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.FriendShipRequestDto;
import dev.backend.tutor.dtos.MessageDto;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.AlreadyFriendsException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.validation.StudentValidationService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
        String content = recipientLogin + ", user " + senderLogin + " wants to become your friend";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return new MessageDto(senderLogin, recipientLogin, content, sdf1.format(timestamp));
    }

    private void sendRequestToRecipient(
            String recipientLogin, MessageDto messageDto) {
        messageService.sendMessageToUser(recipientLogin, messageDto);
    }

    private void validateIfTheyCanBecomeFriends(String senderLogin, String recipientLogin)
            throws AlreadyFriendsException, NotFoundUserException {
        List<Student> studentThatAboutToBeFriends =  studentRepository
                .findSenderAndRecipientStudentsWithFriendsAndBlocked(senderLogin,recipientLogin);
        var senderStudent = extractStudentFromListByUsername(studentThatAboutToBeFriends, senderLogin);
        var recipientStudent = extractStudentFromListByUsername(studentThatAboutToBeFriends, recipientLogin);
        studentValidationService.validateIfStudentsAreFriends(senderStudent, recipientStudent);
        studentValidationService.validateIfSomeoneBlocked(senderStudent, recipientStudent);
    }

    private Student extractStudentFromListByUsername(List<Student> students, String username) throws NotFoundUserException {
        return students.stream()
                .filter(student -> student.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new NotFoundUserException("cannot find user - " + username));
    }

}
