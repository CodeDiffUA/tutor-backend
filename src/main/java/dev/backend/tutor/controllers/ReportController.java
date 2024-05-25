package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.report.ReportDto;
import dev.backend.tutor.services.report.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report")
@CrossOrigin(originPatterns = "*")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public void createReport(@RequestBody ReportDto reportDto){
        reportService.reportUser(reportDto.username(), reportDto.reportReason(), reportDto.content(), reportDto.date());
    }
}
