package dev.backend.tutor.sevices.auth.signIn;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.WrongCredentialsException;
import dev.backend.tutor.sevices.security.TokenFactory;
import dev.backend.tutor.sevices.student.StudentServiceImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl implements SignInService {

    private final StudentServiceImpl studentService;
    private final TokenFactory tokenFactory;
    private final AuthenticationManager authenticationManager;

    public SignInServiceImpl(StudentServiceImpl studentService, TokenFactory tokenFactory, AuthenticationManager authenticationManager) {
        this.studentService = studentService;
        this.tokenFactory = tokenFactory;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public JwtAndRefreshDto signIn(AuthenticationDtoRequest authenticationDtoRequest) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException, WrongCredentialsException {
        // FIXME: here's extra select query of user
        authenticate(authenticationDtoRequest);
        Student student = studentService.getStudentWithRolesByUsernameOrEmail(authenticationDtoRequest.usernameOrEmail());
        String jwt = tokenFactory.createJwt(student);
        String refreshToken = tokenFactory.createRefreshToken(student).getToken();
        return new JwtAndRefreshDto(jwt, refreshToken);
    }

    private void authenticate(AuthenticationDtoRequest authenticationDtoRequest) throws WrongCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationDtoRequest.usernameOrEmail(),
                            authenticationDtoRequest.password()));
        } catch (DisabledException | LockedException | AccountExpiredException | BadCredentialsException ex) {
            throw new WrongCredentialsException(ex.getMessage());
        } catch (AuthenticationException ex) {
            throw new WrongCredentialsException("Authentication failed: " + ex.getMessage());
        }
    }

}
