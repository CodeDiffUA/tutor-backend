package dev.backend.tutor.services.nofications;

import dev.backend.tutor.entities.messegeEntities.Notification;

/**
 * Service interface for handling notifications.
 */
public interface NotificationService {

    /**
     * Notifies the user with the given notification.
     *
     * @param notification The notification to be sent.
     */
    void notifyUser(Notification notification);
}
