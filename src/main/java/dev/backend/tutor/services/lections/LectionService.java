package dev.backend.tutor.services.lections;

import dev.backend.tutor.exceptions.NoSubjectException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface LectionService {
    Map<String, Object> getThemeByNameWithStream(String themeName);

    List<Map<String, Object>>  getThemeNames(String path) throws NoSubjectException, IOException;

    Map<String, Object> getThemesByGlobalName(String globalName);
}
