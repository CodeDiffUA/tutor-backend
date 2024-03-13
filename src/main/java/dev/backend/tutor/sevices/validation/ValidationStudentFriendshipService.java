package dev.backend.tutor.sevices.validation;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.frienship.AlreadyFriendsException;
import dev.backend.tutor.exceptions.frienship.BlockedUsersException;
import org.springframework.transaction.annotation.Transactional;

/**
 * This interface provides methods for validating student friendships.
 */
public interface ValidationStudentFriendshipService {

    /**
     * Validates if the two students are already friends.
     *
     * @param senderStudent The sender student.
     * @param recipientStudent The recipient student.
     * @throws AlreadyFriendsException if the sender and recipient are already friends.
     */
    @Transactional(readOnly = true)
    void validateIfStudentsAreFriends(Student senderStudent, Student recipientStudent) throws AlreadyFriendsException;

    /**
     * Validates if either the sender or recipient has blocked the other.
     *
     * @param senderStudent The sender student.
     * @param recipientStudent The recipient student.
     * @throws BlockedUsersException if either the sender or recipient has blocked the other.
     */
    @Transactional(readOnly = true)
    void validateIfSomeoneBlocked(Student senderStudent, Student recipientStudent) throws BlockedUsersException;
}
