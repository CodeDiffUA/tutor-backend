package dev.backend.tutor.repositories.sql.notification;

import dev.backend.tutor.entities.messegeEntities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
