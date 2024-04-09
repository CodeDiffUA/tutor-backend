package dev.backend.tutor.entities;


import dev.backend.tutor.entities.auth.Role;
import dev.backend.tutor.entities.auth.UserRole;
import dev.backend.tutor.entities.messegeEntities.Notification;
import dev.backend.tutor.utills.student.Form;
import dev.backend.tutor.utills.student.StudentBuilder;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "accounts")
public class Student implements UserDetails {
    @Id
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private Integer age; //todo make datetime
    private Form form;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<GeneralGrades> generalGradesList = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<Notification> notifications = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "student_id", unique = true),
            inverseJoinColumns = @JoinColumn(name = "friend_id", unique = true)
    )
    private List<Student> friends = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<UserRole> roles = new HashSet<>();

    public void addRole(UserRole userRole) {
        userRole.setStudent(this);
        roles.add(userRole);
    }

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
            joinColumns = @JoinColumn(name = "student_id", unique = true),
            inverseJoinColumns = @JoinColumn(name = "blocked_id", unique = true)
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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        /*
         Locking a user account is typically used as a security measure in response to multiple failed login attempts,
         to prevent brute force attacks or unauthorized access. For example, after a certain number of failed login attempts,
         an account may be locked for a specific duration or until the user performs a password reset.
         */
        // fixme implement user locking
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return !isBanned() && !isNotActivated();
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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

    public Integer getAge() {
        return age;
    }

    public Form getForm() {
        return form;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBanned() {
        return containsRole(Role.ROLE_BANNED);
    }
    public boolean isNotActivated() {
        return containsRole(Role.ROLE_UNACTIVATED);
    }

    public void activate() {
        if (isNotActivated()) {
            roles.remove(new UserRole(this, Role.ROLE_UNACTIVATED));
        }
    }


    private boolean containsRole(Role role){
        for (UserRole userRole : roles) {
            if (userRole.getRole() == role) {
                return true;
            }
        }
        return false;
    }


    public void banStudent() {
        this.roles.add(new UserRole(this, Role.ROLE_BANNED));
    }

    public void unbanStudent() {
        this.roles.remove(new UserRole(this, Role.ROLE_BANNED));
    }
}
