package dev.backend.tutor.sevices.authentication;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.WrongPasswordOrUsernameException;
import dev.backend.tutor.sevices.security.JwtBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final JwtBuilder jwtBuilder;
    private final AuthenticationManager authenticator;

    public AuthenticationServiceImpl(UserDetailsService userDetailsService, JwtBuilder jwtBuilder, AuthenticationManager authenticator) {
        this.userDetailsService = userDetailsService;
        this.jwtBuilder = jwtBuilder;
        this.authenticator = authenticator;
    }

    @Override
    public AuthenticationResponseDto signIn(AuthenticationDtoRequest authenticationDtoRequest) throws WrongPasswordOrUsernameException, NotFoundUserException {
        getAuthentication(authenticationDtoRequest.usernameOrEmail(), authenticationDtoRequest.password());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDtoRequest.usernameOrEmail());
        String jwt = jwtBuilder.generateJwt(userDetails);
        return new AuthenticationResponseDto(jwt, null);
    }

    private void getAuthentication(String usernameOrEmail, String password) throws WrongPasswordOrUsernameException {
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
        try {
            authenticator.authenticate(usernamePasswordToken);
        } catch (AuthenticationException e) {
            throw new WrongPasswordOrUsernameException("wrong credentials");
        }
    }

}
