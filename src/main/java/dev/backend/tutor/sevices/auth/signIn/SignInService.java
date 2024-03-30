package dev.backend.tutor.sevices.auth.signIn;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SignInService {

    JwtAndRefreshDto signIn(AuthenticationDtoRequest authenticationDtoRequest) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException;
}
