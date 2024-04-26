package dev.backend.tutor.controllers;

import dev.backend.tutor.exceptions.EqualPasswordException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping
    @RolesAllowed("STUDENT")
    public String changePassword() throws EqualPasswordException {
        return "hello";
    }
}
