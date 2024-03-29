package dev.backend.tutor.sevices.student;

import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.repositories.student.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        var student = studentRepository.findStudentsByUsernameOrEmailWithRoles(usernameOrEmail).orElseThrow();
        if (!student.isEnabled()) {
            throw new NotConfirmedEmailException("User has not confirmed email yet");
        }
        return new User(usernameOrEmail, student.getPassword(), student.getRoles());
    }
}
