package dev.backend.tutor.sevices.student;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;

public interface RegistrationService {

    void registerAccount(RegistrationDtoRequest registrationDtoRequest);

}
