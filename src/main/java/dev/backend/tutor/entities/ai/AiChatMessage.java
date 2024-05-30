package dev.backend.tutor.entities.ai;


import dev.backend.tutor.entities.student.Student;
import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class AiChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column
    private String content;

    private String role;

    @ManyToOne
    private Student student;

    @ManyToOne
    private AiChat chat;

    public AiChatMessage(String content, String role, Student student, AiChat chat) {
        this.content = content;
        this.role = role;
        this.student = student;
        this.chat = chat;
    }

    public AiChatMessage() {

    }
}
