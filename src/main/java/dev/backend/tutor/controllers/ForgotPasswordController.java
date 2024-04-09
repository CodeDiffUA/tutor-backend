package dev.backend.tutor.controllers;


import dev.backend.tutor.dtos.forgotPassword.ForgotPasswordDtoRequest;
import dev.backend.tutor.exceptions.EqualPasswordException;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.forgotPassword.ForgotPasswordServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forgot-password")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {

    private final ForgotPasswordServiceImpl forgotPassword;

    public ForgotPasswordController(ForgotPasswordServiceImpl forgotPassword, StudentRepository studentRepository) {
        this.forgotPassword = forgotPassword;
    }

    @PatchMapping
    public ResponseEntity<Void> changePassword(@RequestBody ForgotPasswordDtoRequest forgotPasswordDtoRequest) throws EqualPasswordException {
        forgotPassword.forgotPassword(forgotPasswordDtoRequest);
        return ResponseEntity.ok().build();
    }
}
