package dev.backend.tutor.repositories.passwords;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.confirmationPasswordToken.ConfirmationPasswordToken;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CustomConfirmationPasswordTokenRepositoryImpl implements CustomConfirmationPasswordTokenRepository{
    private final EntityManager entityManager;

    public CustomConfirmationPasswordTokenRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Student findStudentByConfirmationPasswordToken(String token) {
        System.out.println("dsa");
        List<Student> students = entityManager.createNativeQuery("""
            SELECT a.username, a.email, a.password, a.form, a.age
            FROM confirmation_password_tokens
            LEFT JOIN accounts a on confirmation_password_tokens.student_username = a.username
            WHERE token = ?
            """, Student.class)
                .setParameter(1, token)
                .getResultList();
        return students.get(0);
    }
    @Override
    @Transactional
    public ConfirmationPasswordToken insert(ConfirmationPasswordToken confirmationPasswordToken) {
        Long nextId = (Long) entityManager.createNativeQuery("SELECT nextval('confirmation_tokens_seq')").getSingleResult();
        confirmationPasswordToken.setId(nextId);
        entityManager.createNativeQuery("""
                        INSERT INTO confirmation_password_tokens (expiry_date,student_username,token,id)\s
                        VALUES (?, ?, ?, ?)
                        """)
                .setParameter(1, confirmationPasswordToken.getExpiryDate())
                .setParameter(2, confirmationPasswordToken.getStudent().getUsername())
                .setParameter(3, confirmationPasswordToken.getToken())
                .setParameter(4, confirmationPasswordToken.getId())
                .executeUpdate();
        return confirmationPasswordToken;
    }
}
