package dev.backend.tutor.controllers;

import dev.backend.tutor.exceptions.NoSubjectException;
import dev.backend.tutor.sevices.lections.LectionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/api/v1/lectures")
public class LectionController {
    private final LectionService lectionService;

    public LectionController(LectionService lectionService) {
        this.lectionService = lectionService;
    }

    @GetMapping("ukr_mova/theme")
    public Map<String, Object> getThemeByName(@RequestParam String themeName) {
        return lectionService.getThemeByName(themeName);
    }

    @GetMapping("/themes")
    public List<Map<String, Object>>  getThemes(@RequestParam String subject) throws NoSubjectException {
        return lectionService.getThemeNames(subject);
    }

    @GetMapping("/ukr_mova/global_theme")
    public Map<String, Object> getThemesByGlobalName(@RequestParam String globalName) {
        return lectionService.getThemesByGlobalName(globalName);
    }
}