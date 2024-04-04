package dev.backend.tutor.repositories.emails;

import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Repository
public class CustomConfirmationEmailTokenRepositoryImpl implements CustomConfirmationEmailTokenRepository {

    private final EntityManager entityManager;

    public CustomConfirmationEmailTokenRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public ConfirmationEmailToken insert(ConfirmationEmailToken confirmationEmailToken) {
        Long nextId = (Long) entityManager.createNativeQuery("SELECT nextval('confirmation_tokens_seq')").getSingleResult();
        confirmationEmailToken.setId(nextId);
        entityManager.createNativeQuery("""
                        INSERT INTO confirmation_tokens (expiry_date,student_username,token,id)\s
                        VALUES (?, ?, ?, ?)
                        """)
                .setParameter(1, confirmationEmailToken.getExpiryDate())
                .setParameter(2, confirmationEmailToken.getStudent().getUsername())
                .setParameter(3, confirmationEmailToken.getToken())
                .setParameter(4, confirmationEmailToken.getId())
                .executeUpdate();
        return confirmationEmailToken;
    }
}
