package dev.backend.tutor.entities.student;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.auth.Role;
import dev.backend.tutor.entities.auth.UserRole;
import dev.backend.tutor.utills.student.Form;

public class StudentBuilder {
    private String username;
    private String email;
    private String password;
    private Integer age;
    private Form form;

    public StudentBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public StudentBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public StudentBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public StudentBuilder withAge(Integer age) {
        this.age = age;
        return this;
    }

    public StudentBuilder withForm(Form form) {
        this.form = form;
        return this;
    }


    public Student build() {
        Student student = new Student();
        student.setUsername(this.username);
        student.setEmail(this.email);
        student.setPassword(this.password);
        student.setAge(this.age);
        student.setForm(this.form);
        if (student.getAuthorities().isEmpty()) {
            student.addRole(new UserRole(student, Role.ROLE_UNACTIVATED));
            student.addRole(new UserRole(student, Role.ROLE_STUDENT));
        }
        return student;
    }

    public static Student buildStudent(RegistrationDtoRequest registrationDtoRequest){
        var password = registrationDtoRequest.password();
        return Student.builder()
                .withUsername(registrationDtoRequest.username())
                .withEmail(registrationDtoRequest.email())
                .withPassword(password)
                .withAge(registrationDtoRequest.age())
                .withForm(registrationDtoRequest.form())
                .build();
    }
}
