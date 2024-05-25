package dev.backend.tutor.security.tokensAuth;

import dev.backend.tutor.security.tokensAuth.deserializers.AccessTokenDeserializer;
import dev.backend.tutor.security.tokensAuth.deserializers.RefreshTokenDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationConverter implements AuthenticationConverter {

    private final AccessTokenDeserializer accessTokenStringDeserializer;

    private final RefreshTokenDeserializer refreshTokenStringDeserializer;

    public JwtAuthenticationConverter(AccessTokenDeserializer accessTokenStringDeserializer, RefreshTokenDeserializer refreshTokenStringDeserializer) {
        this.accessTokenStringDeserializer = accessTokenStringDeserializer;
        this.refreshTokenStringDeserializer = refreshTokenStringDeserializer;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            var token = authorization.replace("Bearer ", "");
            var accessToken = this.accessTokenStringDeserializer.deserialize(token);
            if (accessToken != null) {
                System.out.println(accessToken);
                return new PreAuthenticatedAuthenticationToken(accessToken, token);
            }
            var refreshToken = this.refreshTokenStringDeserializer.deserialize(token);
            if (refreshToken != null) {
                System.out.println(refreshToken);
                return new PreAuthenticatedAuthenticationToken(refreshToken, token);
            }
        }
        return null;
    }


}
