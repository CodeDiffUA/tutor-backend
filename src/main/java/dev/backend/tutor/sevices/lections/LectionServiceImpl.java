package dev.backend.tutor.sevices.lections;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.exceptions.NoSubjectException;
import dev.backend.tutor.exceptions.NoThemeException;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class LectionServiceImpl implements LectionService {

    @Override
    public Map<String, Object> getThemeByName(String themeName) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/static/json/ukr_mova_lectures.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(jsonData, Map.class);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Map<String, Object> globalTheme = (Map<String, Object>) entry.getValue();
                if (globalTheme.get("themes") instanceof Map) {
                    Map<String, Map<String, Object>> themes = (Map<String, Map<String, Object>>) globalTheme.get("themes");
                    for (Map.Entry<String, Map<String, Object>> themeEntry : themes.entrySet()) {
                        if (themeEntry.getKey().equals(themeName)) {
                            return themeEntry.getValue();
                        }
                    }
                }
            }

            throw new NoThemeException("Theme not found");
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    @Override
    public List<Map<String, Object>>  getThemeNames(String subject) throws NoSubjectException {
        String UKR_MOVA = "src/main/resources/static/json/ukr_mova_lectures.json";
        String MATH = "src/main/resources/static/json/math_lectures.json";
        String ENGLISH = "src/main/resources/static/json/english_lectures.json";
        String path = switch (subject) {
            case "ukr_mova" -> UKR_MOVA;
            case "math" -> MATH;
            case "english" -> ENGLISH;
            default -> throw new NoSubjectException("No such subject");
        };
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(jsonData, Map.class);

            List<Map<String, Object>> result = new ArrayList<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Map<String, Object> globalTheme = (Map<String, Object>) entry.getValue();
                Map<String, Object> themeResult = new HashMap<>();
                themeResult.put("name", Arrays.asList(entry.getKey(), globalTheme.get("ukr_name")));
                themeResult.put("points", Arrays.asList(-1, -1));

                List<Map<String, Object>> lectures = new ArrayList<>();
                if (globalTheme.get("themes") instanceof Map) {
                    Map<String, Map<String, Object>> themes = (Map<String, Map<String, Object>>) globalTheme.get("themes");
                    for (Map.Entry<String, Map<String, Object>> themeEntry : themes.entrySet()) {
                        Map<String, Object> theme = themeEntry.getValue();
                        Map<String, Object> lecture = new HashMap<>();
                        lecture.put("theme_names", Arrays.asList(themeEntry.getKey(), theme.get("theme_name_ukr"), false));
                        lectures.add(lecture);
                    }
                }
                themeResult.put("lectures", lectures);
                result.add(themeResult);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }
    @Override
    public Map<String, Object> getThemesByGlobalName(String globalName) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/static/json/ukr_mova_lectures.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(jsonData, Map.class);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals(globalName)) {
                    Map<String, Object> globalTheme = (Map<String, Object>) entry.getValue();
                    if (globalTheme.get("themes") instanceof Map) {
                        var eng_name = entry.getKey();
                        var ukr_name = globalTheme.get("ukr_name");
                        var points = new ArrayList<>(List.of(-1, -1));
                        List<Map<String, Object>> content = new ArrayList<>();
                        List<Map<String, Object>> lectures = new ArrayList<>();
                        if (globalTheme.get("themes") instanceof Map) {
                            Map<String, Map<String, Object>> themes = (Map<String, Map<String, Object>>) globalTheme.get("themes");
                            for (Map.Entry<String, Map<String, Object>> themeEntry : themes.entrySet()) {
                                Map<String, Object> theme = (Map<String, Object>) themeEntry.getValue();
                                var theme_name_eng = themeEntry.getKey();
                                var theme_name_ukr = theme.get("theme_name_ukr");
                                var is_complete = false;
                                List<Object> theme_names = new ArrayList<>(List.of(theme_name_eng, theme_name_ukr, is_complete));
                                Map<String, Object> lecture = new HashMap<>();
                                lecture.put("theme_names", theme_names);
                                lectures.add(lecture);
                            }
                        }
                        List<Map<String, Object>> practices = new ArrayList<>();
                        var practice_name_eng = "empty";
                        var practice_name_ukr = "empty";
                        var points_practice = new ArrayList<>(List.of(-1, -1));
                        Map<String, Object> practice = new HashMap<>();
                        practice.put("practice_name", List.of(practice_name_eng, practice_name_ukr, points_practice ));
                        practices.add(practice);
                        content.add(Map.of("lectures", lectures));
                        content.add(Map.of("practices", practices));
                        return Map.of("name", List.of(eng_name, ukr_name), "points", points, "content", content);
                    }
                }
            }

            throw new NoThemeException("Global theme not found");
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }
}

