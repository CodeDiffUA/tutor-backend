package dev.backend.tutor.services.auth.signIn;

import dev.backend.tutor.security.userDetails.StudentUserDetailsService;
import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.WrongCredentialsException;
import dev.backend.tutor.repositories.sql.refresh.RefreshTokenRepository;
import dev.backend.tutor.services.security.TokenFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl implements SignInService {

    private final StudentUserDetailsService studentService;
    private final TokenFactory tokenFactory;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    public SignInServiceImpl(StudentUserDetailsService studentService, TokenFactory tokenFactory, AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository) {
        this.studentService = studentService;
        this.tokenFactory = tokenFactory;
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public JwtAndRefreshDto signIn(AuthenticationDtoRequest authenticationDtoRequest) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException, WrongCredentialsException {
        // FIXME: here's extra select query of user
        authenticate(authenticationDtoRequest);
        Student student = studentService.getStudentWithRolesByUsernameOrEmail(authenticationDtoRequest.usernameOrEmail());
        String jwt = tokenFactory.createJwt(student);
        RefreshToken refreshToken = tokenFactory.createRefreshToken(student);
        refreshTokenRepository.insert(refreshToken);
        return new JwtAndRefreshDto(jwt, refreshToken.getToken());
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
