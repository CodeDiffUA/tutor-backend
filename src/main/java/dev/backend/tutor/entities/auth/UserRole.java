package dev.backend.tutor.entities.auth;

import dev.backend.tutor.entities.Student;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name = "user_roles")
public class UserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_username")
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public UserRole() {
    }

    public UserRole(Student student, Role role) {
        this.student = student;
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getRoleName();
    }
}
