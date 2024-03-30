package dev.backend.tutor.sevices.registration;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.dtos.auth.RegistrationDtoResponse;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {

    /**
     * register account if request valid
     * @param registrationDtoRequest dto request from ui
     * @return RegistrationDtoResponse dto with token for confirmation
     * @throws AlreadyExistsUserException if request is not valid
     */
    RegistrationDtoResponse registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException, NotFoundUserException;

}
