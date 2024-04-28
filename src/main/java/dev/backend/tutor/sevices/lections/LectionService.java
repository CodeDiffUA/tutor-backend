package dev.backend.tutor.sevices.lections;

import dev.backend.tutor.exceptions.NoSubjectException;

import java.util.List;
import java.util.Map;

public interface LectionService {
    Map<String, Object> getThemeByName(String themeName);

    List<Map<String, Object>>  getThemeNames(String path) throws NoSubjectException;

    Map<String, Object> getThemesByGlobalName(String globalName);
}
