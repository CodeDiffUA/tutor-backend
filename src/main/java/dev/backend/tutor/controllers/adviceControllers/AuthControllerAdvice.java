package dev.backend.tutor.controllers.adviceControllers;

import dev.backend.tutor.dtos.message.ExceptionDto;
import dev.backend.tutor.exceptions.*;
import dev.backend.tutor.utills.student.DateUtil;
import io.jsonwebtoken.ExpiredJwtException;
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

    @ExceptionHandler(BannedException.class)
    public ResponseEntity<?> BannedException(BannedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(CookieException.class)
    public ResponseEntity<?> CookieException(CookieException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<?> WrongCredentialsException(WrongCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }


}
