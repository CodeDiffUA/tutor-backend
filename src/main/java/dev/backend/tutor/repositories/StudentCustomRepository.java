package dev.backend.tutor.repositories;

import dev.backend.tutor.entities.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentCustomRepository {
    @Transactional(readOnly = true)
    List<Student> findSenderAndRecipientStudentsWithFriendsAndBlocked(
            String senderLogin, String recipientLogin
    );
}
