package dev.backend.tutor.sevices.student;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.NotFoundUserException;

public interface StudentService {

    /**
     * Load a single student based on the provided login.
     *
     * @param username The login associated with the student.
     * @return The loaded student, or null if not found.
     */
    Student getStudentByUsername(String username) throws NotFoundUserException, AlreadyExistsUserException;


}
