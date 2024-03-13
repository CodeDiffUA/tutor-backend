package dev.backend.tutor.utils;

import dev.backend.tutor.entities.Student;

public class StudentGenerator {

    public static Student generateStudent(int i) {
        return Student.builder()
                .withUsername("username"+i)
                .withEmail("email@gmail.com"+i)
                .withPassword("password")
                .build();
    }

    public static Student generateStudent(String username) {
        return Student.builder()
                .withUsername(username)
                .withEmail("username@gmail.com")
                .withPassword("password")
                .build();
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
