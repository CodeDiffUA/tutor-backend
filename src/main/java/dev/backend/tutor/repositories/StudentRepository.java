package dev.backend.tutor.repositories;

import dev.backend.tutor.entities.Student;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String>, StudentLoader{

    Optional<Student> findStudentByUsername(@Param("username") String username);

    Optional<Student> findStudentByEmail(@Param("email") String email);


    @Query("select s " +
            "from Student s " +
            "left join fetch s.friends " +
            "where s.username=:firstStudentUsername or s.username=:secondStudentUsername"
    )
    List<Student> findStudentsByUsernameFetchFriends(
            @Param("firstStudentUsername") String firstStudentUsername,
            @Param("secondStudentUsername") String secondStudentUsername
    );

    boolean existsStudentByUsername(String username);
    boolean existsStudentByEmail(String email);
}
