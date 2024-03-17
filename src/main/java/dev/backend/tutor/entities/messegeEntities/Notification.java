package dev.backend.tutor.entities.messegeEntities;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.utills.student.NotificationBuilder;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipient_username", nullable = false)
    private Student recipient;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDate timestamp;

    public Notification() {

    }

    public Notification(Student recipient, String content, LocalDate timestamp) {
        this.recipient = recipient;
        this.content = content;
        this.timestamp = timestamp;
    }

    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    public Student getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }
}
