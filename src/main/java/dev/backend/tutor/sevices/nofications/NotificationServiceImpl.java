package dev.backend.tutor.sevices.nofications;

import dev.backend.tutor.dtos.message.NotificationDto;
import dev.backend.tutor.entities.messegeEntities.Notification;
import dev.backend.tutor.repositories.notification.NotificationRepository;
import dev.backend.tutor.utills.student.DateUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(SimpMessagingTemplate messagingTemplate, NotificationRepository repository) {
        this.messagingTemplate = messagingTemplate;
        this.notificationRepository = repository;
    }

    @Override
    public void notifyUser(Notification notification) {
        var recipientLogin = notification.getRecipient().getUsername();
        var timestamp = DateUtil.localDateTimeStampToString(notification.getTimestamp());
        var notificationDto = new NotificationDto(recipientLogin, notification.getContent(), timestamp);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSendToUser(
                recipientLogin, "/queue/notifications", notificationDto
        );
    }
}
