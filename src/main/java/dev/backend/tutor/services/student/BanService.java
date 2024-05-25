package dev.backend.tutor.services.student;

import dev.backend.tutor.repositories.sql.student.StudentRepository;
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
        var student = studentRepository.findStudentsByUsernameOrEmailWithRoles(usernameOrEmail).orElseThrow();
        student.banStudent();
        studentRepository.save(student);
    }

    @Transactional
    public void unbanStudent(String usernameOrEmail) {
        var student = studentRepository.findStudentsByUsernameOrEmailWithRoles(usernameOrEmail).orElseThrow();
        student.unbanStudent();
        studentRepository.save(student);
    }
}
