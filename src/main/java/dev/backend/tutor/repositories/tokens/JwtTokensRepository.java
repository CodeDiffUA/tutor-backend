package dev.backend.tutor.repositories.tokens;

import dev.backend.tutor.config.security.tokensAuth.tokens.Token;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Repository
public class JwtTokensRepository {
    private final EntityManager entityManager;

    public JwtTokensRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void deactivateRefreshToken(Token refreshToken) {
        entityManager.createNativeQuery(
                        "insert into t_deactivated_token (id, c_keep_until) values (?, ?)")
                .setParameter(1, refreshToken.id())
                .setParameter(2, Date.from(refreshToken.expiresAt()))
                .executeUpdate();
    }
    @Transactional
    public boolean checkIfTokenDeactivated(UUID tokenId) {
        return (Boolean) entityManager.createNativeQuery(
                        "select exists(select id from t_deactivated_token where id = ?)")
                .setParameter(1, tokenId)
                .getSingleResult();
    }
}
