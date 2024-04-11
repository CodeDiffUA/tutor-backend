package dev.backend.tutor.repositories.student;

import dev.backend.tutor.entities.Student;

import java.util.List;

public interface StudentCustomRepository {
    List<Student> findSenderAndRecipientStudentsWithFriendsAndBlocked(
            String senderLogin, String recipientLogin
    );

    Student saveStudent(
            Student student
    );
}
