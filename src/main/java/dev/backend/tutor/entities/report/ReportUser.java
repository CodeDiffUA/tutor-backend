package dev.backend.tutor.entities.report;

import jakarta.persistence.*;

@Entity
@Table(name = "report_user")
public class ReportUser {
    @Id
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(name = "report_reason")
    private ReportReason reportReason;
    private String content;
    private String date;

    public String getUsername() {
        return username;
    }

    public ReportReason getReportReason() {
        return reportReason;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = ReportReason.valueOf(reportReason);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
