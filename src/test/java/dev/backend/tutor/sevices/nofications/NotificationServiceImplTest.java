package dev.backend.tutor.sevices.nofications;

import dev.backend.tutor.dtos.message.NotificationDto;
import dev.backend.tutor.entities.messegeEntities.Notification;
import dev.backend.tutor.repositories.notification.NotificationRepository;
import dev.backend.tutor.utills.student.DateUtil;
import dev.backend.tutor.utils.StudentGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl underTest;

    @Test
    void notifyUser_SuccessfullyNotifiesUser() {
        // Arrange
        Notification notification = Notification.builder()
                .withContent("Test content")
                .withRecipient(StudentGenerator.generateStudent("user"))
                .build();

        // Act
        underTest.notifyUser(notification);

        // Assert
        verify(notificationRepository, times(1)).save(notification);
        verify(messagingTemplate, times(1)).convertAndSendToUser(
                notification.getRecipient().getUsername(),
                "/queue/notifications",
                new NotificationDto(
                        notification.getRecipient().getUsername(),
                        notification.getContent(),
                        DateUtil.localDateTimeStampToString(notification.getTimestamp())
                )
        );
    }
}
