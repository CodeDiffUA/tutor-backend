package dev.backend.tutor.repositories.sql.student;

import dev.backend.tutor.entities.student.Student;

import java.util.List;

public interface StudentCustomRepository {
    List<Student> findSenderAndRecipientStudentsWithFriendsAndBlocked(
            String senderLogin, String recipientLogin
    );

    Student saveStudent(Student student);

    List<String> fetchUserRoles(String username);
}
