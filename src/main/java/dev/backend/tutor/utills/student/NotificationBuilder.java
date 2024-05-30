package dev.backend.tutor.utills.student;

import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.entities.messegeEntities.Notification;

import java.time.LocalDate;

public class NotificationBuilder {
    private Student recipient;
    private String content;

    public NotificationBuilder withRecipient(Student recipient) {
        this.recipient = recipient;
        return this;
    }

    public NotificationBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public Notification build() {
        LocalDate currentTimestamp = LocalDate.now();
        return new Notification(recipient, content, currentTimestamp);
    }

}
