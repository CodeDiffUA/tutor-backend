package dev.backend.tutor.repositories.sql.refresh;

import dev.backend.tutor.entities.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, CustomRefreshTokenRepository {

    @Query("select t from RefreshToken t " +
            "left join fetch t.student s " +
            "left join fetch s.roles " +
            "where t.token=:token")
    Optional<RefreshToken> findByTokenWithStudentAndHisRoles(String token);

    Optional<RefreshToken> findRefreshTokenByToken(String token);
}
