package dev.backend.tutor.services.forgotPassword;

import dev.backend.tutor.dtos.forgotPassword.ForgotPasswordDtoRequest;

public interface ForgotPasswordService {
    void forgotPassword(ForgotPasswordDtoRequest forgotPasswordDtoRequest) throws Exception;
}
