package dev.backend.tutor.sevices.auth.signIn;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.security.jwt.JwtBuilder;
import dev.backend.tutor.sevices.security.refresh.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl implements SignInService {

    private final UserDetailsService userDetailsService;
    private final JwtBuilder jwtBuilder;
    private final AuthenticationManager authenticator;
    private final RefreshTokenService refreshTokenService;

    public SignInServiceImpl(UserDetailsService userDetailsService, JwtBuilder jwtBuilder, AuthenticationManager authenticator, RefreshTokenService refreshTokenService) {
        this.userDetailsService = userDetailsService;
        this.jwtBuilder = jwtBuilder;
        this.authenticator = authenticator;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public JwtAndRefreshDto signIn(AuthenticationDtoRequest authenticationDtoRequest) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDtoRequest.usernameOrEmail());
        String jwt = jwtBuilder.generateJwt(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(userDetails).getToken();
        return new JwtAndRefreshDto(jwt, refreshToken);
    }

}
