package dev.backend.tutor.sevices.authentication;

import dev.backend.tutor.repositories.student.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class BanService {

    private final StudentRepository studentRepository;
    public BanService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void banStudent(String usernameOrEmail) {
        var student = studentRepository.findStudentsByUsernameOrEmail(usernameOrEmail).orElseThrow();
        student.setIsBanned(true);
        studentRepository.save(student);
    }

    public void unbanStudent(String usernameOrEmail) {
        var student = studentRepository.findStudentsByUsernameOrEmail(usernameOrEmail).orElseThrow();
        student.setIsBanned(false);
        studentRepository.save(student);
    }
}
