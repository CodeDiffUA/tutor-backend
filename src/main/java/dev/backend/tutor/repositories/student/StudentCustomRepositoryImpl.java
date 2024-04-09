package dev.backend.tutor.repositories.student;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.UserRole;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    private final EntityManager entityManager;

    public StudentCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Student> findSenderAndRecipientStudentsWithFriendsAndBlocked(
            String senderLogin, String recipientLogin
    ) {
        List<Student> students = entityManager.createQuery("""
                        select distinct s
                        from Student s
                        left join fetch s.friends
                        where s.username=:senderLogin or s.username=:recipientLogin
                        """, Student.class)
                .setParameter("senderLogin", senderLogin)
                .setParameter("recipientLogin", recipientLogin)
                .getResultList();

        students = entityManager.createQuery("""
                        select distinct s
                        from Student s
                        left join fetch s.blockedStudents
                        where s in :students
                        """, Student.class)
                .setParameter("students", students)
                .getResultList();

        return students;
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        insertStudentToDB(student);
        insertStudentsRolesToDB(student);
        return student;
    }

    private void insertStudentsRolesToDB(Student student) {
        for (UserRole role : student.getRoles()) {
            Long nextId = (Long) entityManager.createNativeQuery("SELECT nextval('user_roles_seq')").getSingleResult();
            role.setId(nextId);
            entityManager.createNativeQuery("""
                        INSERT INTO user_roles (id, role, student_username)\s
                        VALUES (?, ?, ?)
                        """)
                    .setParameter(1, nextId)
                    .setParameter(2, role.getRole().getRoleName())
                    .setParameter(3, student.getUsername())
                    .executeUpdate();
        }
    }

    private void insertStudentToDB(Student student) {
        entityManager.createNativeQuery("""
                        INSERT INTO accounts (username, email, password, age, form)\s
                        VALUES (?, ?, ?, ?, ?)
                        """)
                .setParameter(1, student.getUsername())
                .setParameter(2, student.getEmail())
                .setParameter(3, student.getPassword())
                .setParameter(4, student.getAge())
                .setParameter(5, student.getForm())
                .executeUpdate();
    }


}
