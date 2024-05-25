package dev.backend.tutor.repositories.sql.refresh;

import dev.backend.tutor.entities.auth.RefreshToken;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomRefreshTokenRepositoryImpl implements CustomRefreshTokenRepository {

    private final EntityManager entityManager;

    public CustomRefreshTokenRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public RefreshToken insert(RefreshToken refreshToken) {
        Long nextId = (Long) entityManager.createNativeQuery("SELECT nextval('refresh_tokens_seq')").getSingleResult();
        refreshToken.setId(nextId);
        entityManager.createNativeQuery("""
                        INSERT INTO refresh_tokens (expiry_date,student_username,token,id)\s
                        VALUES (?, ?, ?, ?)
                        """)
                .setParameter(1, refreshToken.getExpiryDate())
                .setParameter(2, refreshToken.getStudent().getUsername())
                .setParameter(3, refreshToken.getToken())
                .setParameter(4, refreshToken.getId())
                .executeUpdate();
        return refreshToken;
    }

}
