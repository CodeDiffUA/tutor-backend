package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.messages.FriendshipResponseDto;
import dev.backend.tutor.dtos.messages.SystemMessageDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.friendship.response.FriendshipResponseServiceImpl;
import dev.backend.tutor.utils.StudentGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class FriendshipResponseServiceImplTest {

    @InjectMocks
    private FriendshipResponseServiceImpl friendshipResponseService;

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private MessageSender messageSender;

    @Test
    void responseFriendship_Accepted() throws NotFoundUserException {
        // Arrange
        var senderStudent = StudentGenerator.generateStudent("sender");
        var recipientStudent = StudentGenerator.generateStudent("recipient");
        FriendshipResponseDto dto = new FriendshipResponseDto("sender", "recipient", true);
        when(studentRepository.findStudentsByUsernameFetchFriends("sender", "recipient"))
                .thenReturn(List.of(senderStudent, recipientStudent));
        // Act
        friendshipResponseService.responseFriendship(dto);

        // Assert
        ArgumentCaptor<SystemMessageDto> systemMessageCaptor = ArgumentCaptor.forClass(SystemMessageDto.class);
        verify(messageSender).sendSystemMessageToUser(eq("recipient"), systemMessageCaptor.capture());
        Assertions.assertThat(senderStudent.getFriends()).contains(recipientStudent);
        Assertions.assertThat(recipientStudent.getFriends()).contains(senderStudent);
    }

    @Test
    void responseFriendship_Declined() throws NotFoundUserException {
        // Arrange
        var messageDto = new SystemMessageDto("sender", "content", "timestamp");
        FriendshipResponseDto dto = new FriendshipResponseDto("sender", "recipient", false);

        // Act
        friendshipResponseService.responseFriendship(dto);

        // Assert
        verify(messageSender).sendSystemMessageToUser("recipient", messageDto);
    }
}