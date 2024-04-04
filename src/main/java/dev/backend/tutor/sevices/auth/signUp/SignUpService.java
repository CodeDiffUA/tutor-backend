package dev.backend.tutor.sevices.auth.signUp;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.NotFoundUserException;

public interface SignUpService {

    /**
     * register account if request valid
     * @param registrationDtoRequest dto request from ui
     * @return JwtAndRefreshDto
     * @throws AlreadyExistsUserException if request is not valid
     */
    JwtAndRefreshDto registerAccount(RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException, NotFoundUserException;

}
