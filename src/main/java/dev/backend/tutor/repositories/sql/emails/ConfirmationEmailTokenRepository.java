package dev.backend.tutor.repositories.sql.emails;

import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConfirmationEmailTokenRepository extends JpaRepository<ConfirmationEmailToken, Long>, CustomConfirmationEmailTokenRepository {


    @Query("select token from ConfirmationEmailToken token " +
            "left join fetch token.student " +
            "where token.token=:token")
    Optional<ConfirmationEmailToken> findByTokenWithStudent(String token);

    @Query("select token from ConfirmationEmailToken token " +
            "left join fetch token.student s " +
            "left join fetch s.roles " +
            "where token.token=:token")
    Optional<ConfirmationEmailToken> findByTokenWithStudentWithRoles(String token);
    
    Optional<ConfirmationEmailToken> findByToken(String token);
}
