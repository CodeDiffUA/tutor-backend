package dev.backend.tutor.entities;


import dev.backend.tutor.utills.student.Form;
import dev.backend.tutor.utills.student.StudentBuilder;
import jakarta.persistence.*;

import java.util.ArrayList;
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

    private Integer age; //todo make datetime
    private Form form;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<CreditCard> creditCardsList;
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<GeneralGrades> generalGradesList = new ArrayList<>();

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
    }

    public void unblockStudent(Student student) {
        blockedStudents.remove(student);
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

    @Override
    public String toString() {
        return "Student{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", form=" + form +
                ", friends=" + friends.stream().map(Student::getUsername).toList() +
                ", blockedStudents=" + blockedStudents.stream().map(Student::getUsername).toList() +
                '}';
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
