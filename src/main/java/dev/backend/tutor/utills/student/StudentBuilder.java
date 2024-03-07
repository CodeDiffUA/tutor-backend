package dev.backend.tutor.utills.student;

import dev.backend.tutor.entities.CreditCard;
import dev.backend.tutor.entities.Student;

import java.util.List;

public class StudentBuilder {
    private String username;
    private String email;
    private String password;
    private Integer age;
    private Form form;
    private List<CreditCard> creditCardsList;

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
        return student;
    }
}
