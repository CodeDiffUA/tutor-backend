package dev.backend.tutor.sevices.auth.signIn;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.WrongCredentialsException;
import dev.backend.tutor.sevices.security.jwt.JwtBuilder;
import dev.backend.tutor.sevices.security.refresh.RefreshTokenService;
import dev.backend.tutor.sevices.security.refresh.TokenFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl implements SignInService {

    private final UserDetailsService userDetailsService;
    private final TokenFactory tokenFactory;
    private final RefreshTokenService refreshTokenService;

    public SignInServiceImpl(UserDetailsService userDetailsService, TokenFactory tokenFactory, RefreshTokenService refreshTokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenFactory = tokenFactory;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public JwtAndRefreshDto signIn(AuthenticationDtoRequest authenticationDtoRequest) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException, WrongCredentialsException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDtoRequest.usernameOrEmail());
        checkPassword(userDetails.getPassword(), authenticationDtoRequest.password());
        String jwt = tokenFactory.createJwt(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(userDetails).getToken();
        return new JwtAndRefreshDto(jwt, refreshToken);
    }

    private void checkPassword(String usersPassword, String requestPassword) throws WrongCredentialsException {
        if (!usersPassword.equals(requestPassword)) {
            throw new WrongCredentialsException();
        }
    }

}
