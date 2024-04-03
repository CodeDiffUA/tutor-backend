package dev.backend.tutor.controllers.adviceControllers;

import dev.backend.tutor.dtos.message.ExceptionDto;
import dev.backend.tutor.exceptions.friendship.AlreadyFriendsException;
import dev.backend.tutor.exceptions.friendship.BlockedUsersException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;
import dev.backend.tutor.utills.student.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FriendshipControllerAdvice {
    @ExceptionHandler(AlreadyFriendsException.class)
    public ResponseEntity<?> AlreadyFriendsException(AlreadyFriendsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(BlockedUsersException.class)
    public ResponseEntity<?> BlockedUsersException(BlockedUsersException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }

    @ExceptionHandler(FriendshipException.class)
    public ResponseEntity<?> FriendshipException(FriendshipException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(
                        e.getMessage(),
                        DateUtil.currentTimeStamp()
                ));
    }
}
