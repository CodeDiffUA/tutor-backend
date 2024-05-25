package dev.backend.tutor.security.tokensAuth;

import dev.backend.tutor.security.tokensAuth.factories.CookieTokenFactory;
import dev.backend.tutor.security.tokensAuth.serializers.CookieTokenSerializer;
import dev.backend.tutor.services.cookie.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

@Component
public class TokenCookieSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    private static final String AUTH_COOKIE_NAME = "__Host-auth-token";

    private final CookieTokenFactory tokenCookieFactory;
    private final CookieTokenSerializer tokenStringSerializer;
    private final CookieService cookieService;

    public TokenCookieSessionAuthenticationStrategy(CookieTokenFactory tokenCookieFactory, CookieTokenSerializer tokenStringSerializer, CookieService cookieService) {
        this.tokenCookieFactory = tokenCookieFactory;
        this.tokenStringSerializer = tokenStringSerializer;
        this.cookieService = cookieService;
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request,
                                 HttpServletResponse response) throws SessionAuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            var token = this.tokenCookieFactory.createToken(authentication);
            var tokenString = this.tokenStringSerializer.serialize(token);
            var cookie = cookieService.createSecureHttpOnlyTokenCookie(
                    AUTH_COOKIE_NAME, tokenString, token.expiresAt());
            response.addCookie(cookie);
        }
    }
}
