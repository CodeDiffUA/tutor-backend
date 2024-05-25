package dev.backend.tutor.repositories.sql.report;

import dev.backend.tutor.entities.report.ReportUser;
import org.springframework.transaction.annotation.Transactional;

public interface ReportUserCustomRepository {
    @Transactional
    void saveReport(ReportUser user);
}
