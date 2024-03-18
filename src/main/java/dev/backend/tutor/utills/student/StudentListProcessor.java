package dev.backend.tutor.utills.student;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.NotFoundUserException;

import java.util.List;

public class StudentListProcessor {
    public static Student extractStudentFromListByUsername(List<Student> students, String username) throws NotFoundUserException {
        return students.stream()
                .filter(student -> student.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> {
                    var message = "cannot find user - " + username;
                    return new NotFoundUserException(message);
                });
    }
}
