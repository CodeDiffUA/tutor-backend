package dev.backend.tutor.entities.report;

public enum ReportReason {
    SPAM("SPAM"),
    VIOLENCE("VIOLENCE"),
    SEXUAL_CONTENT("SEXUAL_CONTENT"),
    PERSONAL_ATTACK("PERSONAL_ATTACK"),
    HATE_SPEECH("HATE_SPEECH"),
    FALSE_INFORMATION("FALSE_INFORMATION"),
    OTHER("OTHER");

    private final String reportReason;

    public String getReportReason() {
        return reportReason;
    }

    ReportReason(String reportReason) {
        this.reportReason = reportReason;
    }
}
