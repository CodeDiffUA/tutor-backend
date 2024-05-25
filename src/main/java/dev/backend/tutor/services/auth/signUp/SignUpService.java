package dev.backend.tutor.services.auth.signUp;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import org.springframework.transaction.annotation.Transactional;

public interface SignUpService {

    /**
     * register account if request valid
     * @param registrationDtoRequest dto request from ui
     * @throws AlreadyExistsUserException if request is not valid
     */
    void registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException, NotFoundUserException;

    @Transactional
    JwtAndRefreshDto registerAccountWithLogin(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException, NotFoundUserException;
}
