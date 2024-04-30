package dev.backend.tutor.repositories.report;

import dev.backend.tutor.entities.report.ReportUser;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ReportUserCustomRepositoryImpl implements ReportUserCustomRepository {
    private final EntityManager entityManager;

    public ReportUserCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveReport(ReportUser user) {
        entityManager.createNativeQuery("INSERT INTO report_user (username, ban_reason, content, date) VALUES (?, ?, ?, ?)")
                .setParameter(1, user.getUsername())
                .setParameter(2, user.getReportReason())
                .setParameter(3, user.getContent())
                .setParameter(4, user.getDate())
                .executeUpdate();
    }
}
