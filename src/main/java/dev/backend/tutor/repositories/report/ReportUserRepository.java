package dev.backend.tutor.repositories.report;

import dev.backend.tutor.entities.report.ReportUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportUserRepository extends JpaRepository<ReportUser, String>, ReportUserCustomRepository {

}
