package dev.backend.tutor.security.tokensAuth;

import dev.backend.tutor.security.tokensAuth.deserializers.AccessTokenDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class TokenCookieAuthenticationConverter implements AuthenticationConverter {

    private final AccessTokenDeserializer tokenCookieStringDeserializer;

    public TokenCookieAuthenticationConverter(AccessTokenDeserializer tokenCookieStringDeserializer) {
        this.tokenCookieStringDeserializer = tokenCookieStringDeserializer;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Stream.of(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("__Host-auth-token"))
                    .findFirst()
                    .map(cookie -> {
                        var token = this.tokenCookieStringDeserializer.deserialize(cookie.getValue());
                        return new PreAuthenticatedAuthenticationToken(token, cookie.getValue());
                    })
                    .orElse(null);
        }
        return null;
    }
}


