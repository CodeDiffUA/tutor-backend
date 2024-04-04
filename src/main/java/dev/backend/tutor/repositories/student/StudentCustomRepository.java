package dev.backend.tutor.repositories.student;

import dev.backend.tutor.entities.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentCustomRepository {
    List<Student> findSenderAndRecipientStudentsWithFriendsAndBlocked(
            String senderLogin, String recipientLogin
    );

    Student insertStudent(
            Student student
    );
}
