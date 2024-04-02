package dev.backend.tutor.controllers.adviceControllers;

import dev.backend.tutor.dtos.message.ExceptionDto;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.utills.student.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistrationControllerAdvice {

    @ExceptionHandler(AlreadyExistsUserException.class)
    public ResponseEntity<?> AlreadyExistsUserException(AlreadyExistsUserException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }
}
