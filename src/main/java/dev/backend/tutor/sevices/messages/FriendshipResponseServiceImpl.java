package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.messages.FriendshipResponseDto;
import dev.backend.tutor.dtos.messages.SystemMessageDto;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendshipResponseServiceImpl implements FriendshipResponseService {

    private final StudentRepository studentRepository;
    private final MessageService messageService;

    public FriendshipResponseServiceImpl(StudentRepository studentRepository, MessageService messageService) {
        this.studentRepository = studentRepository;
        this.messageService = messageService;
    }

    @Override
    @Transactional
    public void responseFriendship(FriendshipResponseDto friendshipResponseDto) throws NotFoundUserException {
        SystemMessageDto systemMessageToUser;
        if (!friendshipResponseDto.acceptedFriendship()) {
            systemMessageToUser = messageService.messageDtoForDecliningFriendshipRequest(friendshipResponseDto.recipient());
        } else {
            establishFriendship(friendshipResponseDto);
            systemMessageToUser = messageService.messageDtoForAcceptingFriendshipRequest(friendshipResponseDto.recipient());
        }
        messageService.sendSystemMessageToUser(friendshipResponseDto.recipient(), systemMessageToUser);
    }

    private void establishFriendship(FriendshipResponseDto friendshipResponseDto) throws NotFoundUserException {
        var students = getStudentsByUsernameInRequest(friendshipResponseDto.sender(), friendshipResponseDto.recipient());
        var studentOne = students.get(0);
        var studentTwo = students.get(1);
        studentOne.addFriend(studentTwo);
    }

    private List<Student> getStudentsByUsernameInRequest(String firstStudentUsername, String secondStudentUsername) throws NotFoundUserException {
        List<Student> fetchedStudents = studentRepository.findStudentsByUsernameFetchFriends(firstStudentUsername, secondStudentUsername);
        checkIfUserFetched(fetchedStudents, firstStudentUsername);
        checkIfUserFetched(fetchedStudents, secondStudentUsername);
        return fetchedStudents;
    }

    private void checkIfUserFetched(List<Student> students, String username) throws NotFoundUserException {
        boolean isUserInList = students.stream().anyMatch(student -> student.getUsername().equals(username));
        if (!isUserInList) {
            throw new NotFoundUserException("Sorry, the requested user (" + username + ") could not be found. Please check the username and try again.");
        }
    }
}
