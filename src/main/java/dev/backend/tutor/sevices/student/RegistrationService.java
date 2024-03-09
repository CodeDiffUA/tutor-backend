package dev.backend.tutor.sevices.student;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;

public interface RegistrationService {

    /**
     * register account if request valid
     * @param registrationDtoRequest dto request from ui
     * @throws AlreadyExistsUserException if request is not valid
     */
    void registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException;

}
