package dev.backend.tutor.entities.messegeEntities;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.utills.student.NotificationBuilder;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.id.uuid.UuidGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sender_username", nullable = false)
    private Student sender;
    @OneToOne
    @JoinColumn(name = "recipient_username", nullable = false)
    private Student recipient;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDate timestamp;

    public Notification() {

    }

    public Notification(Student sender, Student recipient, String content, LocalDate timestamp) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.timestamp = timestamp;
    }

    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

}
