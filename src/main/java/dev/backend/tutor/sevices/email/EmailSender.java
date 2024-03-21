package dev.backend.tutor.sevices.email;

import dev.backend.tutor.dtos.auth.EmailVerificationDtoRequest;

public interface EmailSender {

    void sendVerification(EmailVerificationDtoRequest emailVerificationDtoRequest);

}
