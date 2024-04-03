package dev.backend.tutor.sevices.forgotPassword;

import dev.backend.tutor.dtos.forgotPassword.ForgotPasswordDtoRequest;

public interface ForgotPasswordService {
    void forgotPassword(ForgotPasswordDtoRequest forgotPasswordDtoRequest) throws Exception;
}
