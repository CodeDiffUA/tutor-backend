package dev.backend.tutor.config.security.tokensAuth;

import com.nimbusds.oauth2.sdk.GrantType;
import dev.backend.tutor.config.security.tokensAuth.deserializers.AccessTokenDeserializer;
import dev.backend.tutor.config.security.tokensAuth.deserializers.RefreshTokenDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.text.ParseException;

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
                //                SecurityContextHolder.getContext().setAuthentication(authToken);
                return new PreAuthenticatedAuthenticationToken(accessToken, token);
//                UsernamePasswordAuthenticationToken authorizationToken = new UsernamePasswordAuthenticationToken(
//                        accessToken.subject(),
//                        null,
//                        accessToken.authorities().stream().map(SimpleGrantedAuthority::new).toList());
//                SecurityContextHolder.getContext().setAuthentication(authorizationToken);
//                return authorizationToken;
            }
            var refreshToken = this.refreshTokenStringDeserializer.deserialize(token);
            if (refreshToken != null) {
                System.out.println(refreshToken);
                var authToken = new PreAuthenticatedAuthenticationToken(refreshToken, token);
//                SecurityContextHolder.getContext().setAuthentication(authToken);
                return authToken;
//                UsernamePasswordAuthenticationToken authorizationToken = new UsernamePasswordAuthenticationToken(
//                        refreshToken.subject(),
//                        null,
//                        refreshToken.authorities().stream().map(SimpleGrantedAuthority::new).toList());
//                SecurityContextHolder.getContext().setAuthentication(authorizationToken);
//                return authorizationToken;
            }
        }
        return null;
    }
}
