package dev.backend.tutor.services.auth.signUp.validation;

import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.exceptions.friendship.AlreadyFriendsException;
import dev.backend.tutor.exceptions.friendship.BlockedUsersException;
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
