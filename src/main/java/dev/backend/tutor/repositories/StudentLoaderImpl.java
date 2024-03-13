package dev.backend.tutor.repositories;

import dev.backend.tutor.entities.Student;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentLoaderImpl implements StudentLoader {

    private final EntityManager entityManager;

    public StudentLoaderImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
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
}
