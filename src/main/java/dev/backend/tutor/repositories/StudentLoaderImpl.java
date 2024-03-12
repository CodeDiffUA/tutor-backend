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
                                select s
                                from Student s
                                left join fetch s.friends
                                where s.username=:senderLogin or s.username=:recipientLogin
                                """, Student.class)
                        .setParameter("senderLogin", senderLogin)
                        .setParameter("recipientLogin", recipientLogin)
                        .getResultList();

        List<Student> resultList = new ArrayList<>();
        for (Student student : students) {
            student = entityManager.createQuery("""
                            select distinct s
                            from Student s
                            left join fetch s.blockedStudents
                            where s = :student
                            """, Student.class)
                    .setParameter("student", student)
                    .getSingleResult();
            resultList.add(student);
        }
        return resultList;
    }
}
