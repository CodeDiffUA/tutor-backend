package dev.backend.tutor.sevices.friendship.response;

import dev.backend.tutor.dtos.messages.FriendshipResponseDto;
import dev.backend.tutor.dtos.messages.SystemMessageDto;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.messages.MessageProvider;
import dev.backend.tutor.sevices.messages.MessageSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendshipResponseServiceImpl implements FriendshipResponseService {

    private final StudentRepository studentRepository;
    private final MessageSender messageSender;

    public FriendshipResponseServiceImpl(StudentRepository studentRepository, MessageSender messageSender) {
        this.studentRepository = studentRepository;
        this.messageSender = messageSender;
    }

    @Override
    @Transactional
    public void responseFriendship(FriendshipResponseDto friendshipResponseDto) throws NotFoundUserException {
        SystemMessageDto systemMessageToUser;
        if (!friendshipResponseDto.acceptedFriendship()) {
            systemMessageToUser = MessageProvider.messageDtoForDecliningFriendshipRequest(friendshipResponseDto.recipient());
        } else {
            establishFriendship(friendshipResponseDto);
            systemMessageToUser = MessageProvider.messageDtoForAcceptingFriendshipRequest(friendshipResponseDto.recipient());
        }
        messageSender.sendSystemMessageToUser(friendshipResponseDto.recipient(), systemMessageToUser);
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
