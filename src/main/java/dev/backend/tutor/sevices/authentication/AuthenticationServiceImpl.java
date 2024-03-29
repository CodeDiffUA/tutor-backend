package dev.backend.tutor.sevices.authentication;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.BannedException;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.security.jwt.JwtBuilder;
import dev.backend.tutor.sevices.security.refresh.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final JwtBuilder jwtBuilder;
    private final AuthenticationManager authenticator;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationServiceImpl(UserDetailsService userDetailsService, JwtBuilder jwtBuilder, AuthenticationManager authenticator, RefreshTokenService refreshTokenService) {
        this.userDetailsService = userDetailsService;
        this.jwtBuilder = jwtBuilder;
        this.authenticator = authenticator;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public JwtAndRefreshDto signIn(AuthenticationDtoRequest authenticationDtoRequest) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException {
        getAuthentication(authenticationDtoRequest.usernameOrEmail(), authenticationDtoRequest.password());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDtoRequest.usernameOrEmail());;
        String jwt = jwtBuilder.generateJwt(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(userDetails).getToken();
        return new JwtAndRefreshDto(jwt, refreshToken);
    }

    private void getAuthentication(String usernameOrEmail, String password) throws UsernameNotFoundException, NotConfirmedEmailException {
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
        authenticator.authenticate(usernamePasswordToken);
    }

    private void checkIfBanned(Student student) throws BannedException {
        if (student.getIsBanned()) {
            throw new BannedException("User is banned");
        }
    }
}
