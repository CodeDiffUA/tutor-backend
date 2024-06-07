package dev.backend.tutor.repositories.sql.points;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UkrMovaTestRepository extends JpaRepository<UkrMovaTest, Long> {
}