package dev.backend.tutor.repositories;

import dev.backend.tutor.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
