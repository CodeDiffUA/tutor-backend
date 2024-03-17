package dev.backend.tutor.controllers.adviceControllers;

import dev.backend.tutor.dtos.message.ExceptionDto;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.AlreadyFriendsException;
import dev.backend.tutor.utills.student.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.rmi.AlreadyBoundException;

@ControllerAdvice
public class CredentialController {

    @ExceptionHandler(AlreadyExistsUserException.class)
    public ResponseEntity<?> alreadyExistsUserHandler(AlreadyExistsUserException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(AlreadyFriendsException.class)
    public ResponseEntity<?> alreadyBoundExceptionHandler(AlreadyFriendsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<?> notExistUserHandler(NotFoundUserException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }
}
