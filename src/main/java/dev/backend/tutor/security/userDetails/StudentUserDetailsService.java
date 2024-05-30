package dev.backend.tutor.security.userDetails;

import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.sql.student.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public StudentUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Student student = studentRepository.findStudentsByUsernameOrEmailWithRoles(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("no user - " + usernameOrEmail));
        checkIfBanned(student); // todo: remove it when presentation is
        checkIfActivated(student); // todo: remove it when presentation is
        return student;
    }


    public Student getStudentWithRolesByUsernameOrEmail(String usernameOrEmail) throws NotFoundUserException {
        return studentRepository.findStudentsByUsernameOrEmailWithRoles(usernameOrEmail)
                .orElseThrow(() -> new NotFoundUserException("no " + usernameOrEmail+" in db"));
    }

    public void saveStudent(Student student) {
        studentRepository.saveStudent(student);
    }

    private void checkIfActivated(Student student) {
        if (student.isNotActivated()) {
            throw new NotConfirmedEmailException("User has not confirmed email yet");
        }
    }

    private void checkIfBanned(Student student){
        if (student.isBanned()) {
            throw new UsernameNotFoundException("User is banned");
        }
    }
}
