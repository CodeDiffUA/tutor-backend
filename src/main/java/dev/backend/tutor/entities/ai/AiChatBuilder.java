package dev.backend.tutor.entities.ai;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.student.Student;

public class AiChatBuilder {
    private Long id;
    private String name;
    private Student student;

    public AiChatBuilder name(String name) {
        this.name = name;
        return this;
    }

    public AiChatBuilder student(Student student) {
        this.student = student;
        return this;
    }

    public AiChat build() {
        return new AiChat(name, student);
    }

}
