package dev.backend.tutor.utils;

import dev.backend.tutor.entities.Student;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentGenerator {
    public static Student generateStudent(int i) {
        return Student.builder()
                .withUsername("username" + i)
                .withEmail("email@gmail.com" + i)
                .withPassword("password")
                .build();
    }

    public static Student generateStudent(String username) {
        return Student.builder()
                .withUsername(username)
                .withEmail(username + "@gmail.com")
                .withPassword("password")
                .isEnabled(true)
                .build();
    }

    public static Student generateUnabledStudent(String username) {
        var student = generateStudent(username);
        student.setEnabled(false);
        return student;
    }

    public static Student generateFriendsForStudents(Student student, Integer count) {
        for (int i = 0; i < count; i++) {
            student.addFriend(generateStudent(i));
        }
        return student;
    }

    public static Student generateBlockedForStudents(Student student, Integer count) {
        for (int i = 0; i < count; i++) {
            student.blockStudent(generateStudent(i));
        }
        return student;
    }
}
