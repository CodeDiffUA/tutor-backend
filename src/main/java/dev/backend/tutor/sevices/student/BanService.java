package dev.backend.tutor.sevices.student;

import dev.backend.tutor.repositories.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BanService {

    private final StudentRepository studentRepository;
    public BanService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void banStudent(String usernameOrEmail) {
        var student = studentRepository.findStudentsByUsernameOrEmail(usernameOrEmail).orElseThrow();
        student.setBanned(true);
        studentRepository.save(student);
    }

    @Transactional
    public void unbanStudent(String usernameOrEmail) {
        var student = studentRepository.findStudentsByUsernameOrEmail(usernameOrEmail).orElseThrow();
        student.setBanned(false);
        studentRepository.save(student);
    }
}
