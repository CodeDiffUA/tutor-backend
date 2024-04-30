package dev.backend.tutor.sevices.report;

import dev.backend.tutor.entities.report.ReportUser;
import dev.backend.tutor.repositories.report.ReportUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportUserRepository reportUserRepository;

    public ReportService(ReportUserRepository reportUserRepository) {
        this.reportUserRepository = reportUserRepository;
    }

    public void reportUser(String username, String reportReason, String content, String date) {
        ReportUser reportUser = new ReportUser();
        reportUser.setUsername(username);
        try {
            reportUser.setReportReason(reportReason);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong data type");
        }
        reportUser.setContent(content);
        reportUser.setDate(date);
        reportUserRepository.save(reportUser);
    }
}
