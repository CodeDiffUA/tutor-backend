package dev.backend.tutor.sevices.student;

import dev.backend.tutor.repositories.StudentRepository;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var student = studentRepository.findStudentsByUsernameWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("cannot find user - " + username));
        return new User(username, student.getPassword(), student.getRoles());
    }
}
