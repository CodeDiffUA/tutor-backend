package dev.backend.tutor.services.lections;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.exceptions.NoSubjectException;
import dev.backend.tutor.exceptions.NoThemeException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
public class LectionServiceImpl implements LectionService {
    @Override
    public Map<String, Object> getThemeByNameWithStream(String themeName) {
        try {
            Map<String, Object> map = getMapOfLections("static/json/ukr_mova_lectures.json");
            Optional<Map<String, Object>> optionalTheme = map.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .filter(globalTheme -> globalTheme instanceof Map && ((Map<String, Object>) globalTheme).containsKey("themes"))
                    .map(globalTheme -> (Map<String, Map<String, Object>>) ((Map<String, Object>) globalTheme).get("themes"))
                    .flatMap(themes -> themes.entrySet().stream())
                    .filter(themeEntry -> themeEntry.getKey().equals(themeName))
                    .map(Map.Entry::getValue)
                    .findFirst();
            return optionalTheme.orElseThrow(() -> new NoThemeException("Theme not found"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    public String readFile(final String fileName) throws IOException {
        URL url = this.getClass()
                .getClassLoader()
                .getResource(fileName);

        if(url == null) {
            throw new IllegalArgumentException(fileName + " is not found 1");
        }

        File file = new File(url.getFile());

        return new String(Files.readAllBytes(file.toPath()));
    }


    private Map<String, Object> getMapOfLections(String path) throws IOException {
//        byte[] jsonData = Files.readAllBytes(Paths.get(path));
        ObjectMapper objectMapper = new ObjectMapper();

        var jsonStrong = readFile(path);


        Map<String, Object> map = objectMapper.readValue(jsonStrong, Map.class);
        return map;
    }


    @Override
    public List<Map<String, Object>>  getThemeNames(String subject) throws NoSubjectException, IOException {
        String UKR_MOVA = "static/json/ukr_mova_lectures.json";
        String MATH = "static/json/math_lectures.json";
        String ENGLISH = "static/json/english_lectures.json";
        String path = switch (subject) {
            case "ukr_mova" -> UKR_MOVA;
            case "math" -> MATH;
            case "english" -> ENGLISH;
            default -> throw new NoSubjectException("No such subject");
        };
        var map = getMapOfLections(path);
        Stream<Object> optionalTheme = map.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> globalTheme = (Map<String, Object>) entry.getValue();
                    var eng_name = entry.getKey();
                    var ukr_name = globalTheme.get("ukr_name");
                    return Map.of("name", List.of(eng_name, ukr_name));
                });
        return List.of(optionalTheme.toArray(Map[]::new));
    }

    @Override
    public Map<String, Object> getThemesByGlobalName(String globalName) {
        try {
            Map<String, Object> map = getMapOfLections("static/json/ukr_mova_lectures.json");

            var themeStream = map.entrySet().stream()
                    .filter(entry -> entry.getKey().equals(globalName))
                    .map(entry -> {
                        Map<String, Object> globalTheme = (Map<String, Object>) entry.getValue();
                        var eng_name = entry.getKey();
                        var ukr_name = globalTheme.get("ukr_name");
                        var points = new ArrayList<>(List.of(-1, -1));

                        if (globalTheme.get("themes") instanceof Map) {
                            Map<String, Map<String, Object>> themes = (Map<String, Map<String, Object>>) globalTheme.get("themes");
                            Map<String, Object>[] lectures = themes.entrySet().stream()
                                    .map(themeEntry -> {
                                        Map<String, Object> theme = themeEntry.getValue();
                                        var theme_name_eng = themeEntry.getKey();
                                        var theme_name_ukr = theme.get("theme_name_ukr");
                                        var is_complete = false;
                                        return Map.of("theme_names", List.of(theme_name_eng, theme_name_ukr, is_complete));
                                    })
                                    .toArray(Map[]::new);

                            List<Map<String, Object>> practices = new ArrayList<>();
                            var practice_name_eng = "empty";
                            var practice_name_ukr = "empty";
                            var points_practice = new ArrayList<>(List.of(-1, -1));
                            practices.add(Map.of("practice_name", List.of(practice_name_eng, practice_name_ukr, points_practice)));

                            return Map.of("name", List.of(eng_name, ukr_name), "points", points, "content", Map.of("lectures", lectures, "practices", practices));
                        }
                        return null;
                    })
                    .filter(Objects::nonNull);

            return themeStream.findFirst()
                    .orElseThrow(() -> new NoThemeException("Global theme not found"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }
}