package dev.backend.tutor.controllers.adviceControllers;

import dev.backend.tutor.dtos.ExceptionDto;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CredentialController {

    @ExceptionHandler(AlreadyExistsUserException.class)
    public ResponseEntity<?> tokenExpiredExceptionHandler(AlreadyExistsUserException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(
                        e.getMessage(),
                        new Date()
                ));
    }
}
