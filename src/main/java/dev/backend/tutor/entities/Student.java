package dev.backend.tutor.entities;

import dev.backend.tutor.utills.student.Form;
import dev.backend.tutor.utills.student.StudentBuilder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Student> friends = new ArrayList<>();

    public void addFriend(Student student) {
        friends.add(student);
        student.getFriends().add(this);
    }

    public void removeFriend(Student student) {
        friends.remove(student);
        student.getFriends().remove(this);
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "blocked_users",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_id")
    )
    private List<Student> blockedStudents = new ArrayList<>();

    public void blockStudent(Student student) {
        blockedStudents.add(student);
        student.getBlockedStudents().add(this);
    }

    public void unblockStudent(Student student) {
        blockedStudents.remove(student);
        student.getBlockedStudents().remove(this);
    }


//    constructor and builder

    public Student() {
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(username, student.username)
                && Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    //  getter and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<Student> getFriends() {
        return friends;
    }

    public List<Student> getBlockedStudents() {
        return blockedStudents;
    }
}
