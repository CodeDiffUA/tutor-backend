package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.messegeEntities.Notification;
import dev.backend.tutor.utills.student.NotificationFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotificationFactoryTest {
    @Test
    void testAcceptRequestNotification() {
        Student recipient = mock(Student.class);
        when(recipient.getUsername()).thenReturn("recipient");

        Notification notification = NotificationFactory.acceptRequestNotification(recipient);

        assertEquals("recipient, your friendship request has been accepted", notification.getContent());
        assertEquals(recipient, notification.getRecipient());
    }

    @Test
    void testDeclineRequestNotification() {
        Student recipient = mock(Student.class);
        when(recipient.getUsername()).thenReturn("recipient");

        Notification notification = NotificationFactory.declineRequestNotification(recipient);

        assertEquals("recipient, your friendship request has been declined", notification.getContent());
        assertEquals(recipient, notification.getRecipient());
    }

    @Test
    void testRequestNotification() {
        Student sender = mock(Student.class);
        when(sender.getUsername()).thenReturn("sender");

        Student recipient = mock(Student.class);
        when(recipient.getUsername()).thenReturn("recipient");

        Notification notification = NotificationFactory.requestNotification(sender, recipient);

        assertEquals("recipient, user sender wants to become your friend", notification.getContent());
        assertEquals(recipient, notification.getRecipient());
    }
}

