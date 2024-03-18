package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.friendship.RequestFriendshipResponseDto;
import dev.backend.tutor.dtos.message.NotificationDto;
import dev.backend.tutor.entities.messegeEntities.Notification;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.friendship.response.FriendshipResponseServiceImpl;
import dev.backend.tutor.sevices.nofications.NotificationService;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FriendshipResponseServiceImplTest {

    @InjectMocks
    private FriendshipResponseServiceImpl friendshipResponseService;

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private NotificationService notificationService;

    @Test
    void responseFriendship_Accepted() throws NotFoundUserException {
        // Arrange
        var senderStudent = StudentGenerator.generateStudent("sender");
        var recipientStudent = StudentGenerator.generateStudent("recipient");
        RequestFriendshipResponseDto dto = new RequestFriendshipResponseDto("sender", "recipient", true);

        when(studentRepository.findStudentsByUsernameFetchFriends("sender", "recipient"))
                .thenReturn(List.of(senderStudent, recipientStudent));
        // Act
        friendshipResponseService.responseFriendship(dto);

        // Assert
        verify(notificationService).notifyUser(any(Notification.class));
        Assertions.assertThat(senderStudent.getFriends()).contains(recipientStudent);
        Assertions.assertThat(recipientStudent.getFriends()).contains(senderStudent);
    }

    @Test
    void responseFriendship_Declined() throws NotFoundUserException {
        // Arrange
        var recipientStudent = StudentGenerator.generateStudent("recipient");
        RequestFriendshipResponseDto dto = new RequestFriendshipResponseDto("sender", "recipient", false);

        when(studentRepository.findStudentByUsername("recipient"))
                .thenReturn(Optional.of(recipientStudent));
        // Act
        friendshipResponseService.responseFriendship(dto);

        // Assert
        verify(notificationService).notifyUser(any(Notification.class));
    }
}
