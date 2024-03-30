package dev.backend.tutor.sevices.auth.signUp.validation;

import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import org.springframework.transaction.annotation.Transactional;

/**
 * This interface provides methods for validating student data.
 */
public interface ValidationStudentDataService {

    /**
     * Validates the uniqueness of the provided email address.
     *
     * @param email The email address to be validated.
     * @throws AlreadyExistsUserException if the email address already exists.
     */
    @Transactional(readOnly = true)
    void validateEmail(String email) throws AlreadyExistsUserException;

    /**
     * Validates the uniqueness of the provided username.
     *
     * @param username The username to be validated.
     * @throws AlreadyExistsUserException if the username already exists.
     */
    @Transactional(readOnly = true)
    void validateUsername(String username) throws AlreadyExistsUserException;
}

