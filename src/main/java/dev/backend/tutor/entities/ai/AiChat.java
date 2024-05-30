package dev.backend.tutor.entities.ai;


import dev.backend.tutor.entities.student.Student;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat")
public class AiChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Student student;

    @OneToMany(mappedBy = "chat", orphanRemoval = true)
    private List<AiChatMessage> aiChatMessages = new ArrayList<>();

    public AiChat(Long id, String name, Student student) {
        this.id = id;
        this.name = name;
        this.student = student;
    }

    public AiChat(Long id, String name, Student student, List<AiChatMessage> aiChatMessages) {
        this.id = id;
        this.name = name;
        this.student = student;
        this.aiChatMessages = aiChatMessages;
    }

    public AiChat(String name, Student student) {
        this.name = name;
        this.student = student;
    }

    public AiChat() {
    }


    public static AiChatBuilder builder() {
        return new AiChatBuilder();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAiChatMessages(List<AiChatMessage> aiChatMessages) {
        this.aiChatMessages = aiChatMessages;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Student getStudent() {
        return student;
    }

    public List<AiChatMessage> getAiChatMessages() {
        return aiChatMessages;
    }
}
