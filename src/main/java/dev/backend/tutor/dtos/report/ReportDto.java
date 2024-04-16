package dev.backend.tutor.dtos.report;

public record ReportDto(
        String username,
        String reportReason,
        String content,
        String date
) {
}
