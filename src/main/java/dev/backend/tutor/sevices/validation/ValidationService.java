package dev.backend.tutor.sevices.validation;

import dev.backend.tutor.exceptions.AlreadyExistsUserException;

public interface ValidationService {
    void validateEmail(String email) throws AlreadyExistsUserException;
    void validateUsername(String username) throws AlreadyExistsUserException;
}
