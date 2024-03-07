package dev.backend.tutor.entities;

import dev.backend.tutor.utills.student.Form;
import dev.backend.tutor.utills.student.StudentBuilder;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "accounts")
public class Student {
    @Id
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private Integer age;
    private Form form;

    @OneToMany(mappedBy = "owner")
    private List<CreditCard> creditCardsList;

//    constructor and builder

    public Student() {
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

//  getter and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<CreditCard> getCreditCardsList() {
        return creditCardsList;
    }

    public void setCreditCardsList(List<CreditCard> creditCardsList) {
        this.creditCardsList = creditCardsList;
    }
}
