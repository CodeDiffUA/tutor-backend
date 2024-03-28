package dev.backend.tutor.controllers.adviceControllers;

import dev.backend.tutor.dtos.message.ExceptionDto;
import dev.backend.tutor.exceptions.InvalidJwtException;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.utills.student.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<?> NotFoundUserException(NotFoundUserException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(NotConfirmedEmailException.class)
    public ResponseEntity<?> NotConfirmedEmailException(NotConfirmedEmailException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> UsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> InvalidTokenException(InvalidTokenException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<?> InvalidJwtException(InvalidJwtException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }
}
