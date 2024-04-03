package dev.backend.tutor.repositories.emails;

import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConfirmationEmailTokenRepository extends JpaRepository<ConfirmationEmailToken, Long> {


    @Query("select token from ConfirmationEmailToken token " +
            "left join fetch token.student " +
            "where token.token=:token")
    Optional<ConfirmationEmailToken> findByTokenWithStudent(String token);
    
    Optional<ConfirmationEmailToken> findByToken(String token);
}
