package dev.backend.tutor.security.tokensAuth.filters;

import dev.backend.tutor.security.tokensAuth.TokenCookieSessionAuthenticationStrategy;
import dev.backend.tutor.security.tokensAuth.utills.RequestAuthenticationConverter;
import dev.backend.tutor.exceptions.WrongCredentialsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class CookieSignInFilter extends OncePerRequestFilter {

    private static final String SUCCESSFUL_AUTHENTICATION_MESSAGE = "{\"message\": \"Authentication successful\"}";

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/v2/authentication/login", HttpMethod.POST.name());
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    private final RequestAuthenticationConverter requestAuthenticationConverter;

    private final AuthenticationManager authenticationManager;
    private final TokenCookieSessionAuthenticationStrategy tokenCookieSessionAuthenticationStrategy;


    public CookieSignInFilter(RequestAuthenticationConverter requestAuthenticationConverter, AuthenticationManager authenticationManager, TokenCookieSessionAuthenticationStrategy tokenCookieSessionAuthenticationStrategy) {
        this.requestAuthenticationConverter = requestAuthenticationConverter;
        this.authenticationManager = authenticationManager;
        this.tokenCookieSessionAuthenticationStrategy = tokenCookieSessionAuthenticationStrategy;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        this.logger.info("cookie sign in filter called");
        if (this.requestMatcher.matches(request)) {
            UsernamePasswordAuthenticationToken authenticationToken = requestAuthenticationConverter.convertRequestBodyToAuthenticationToken(request);
            try {
                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                if (authentication.isAuthenticated()) {
                    tokenCookieSessionAuthenticationStrategy.onAuthentication(authentication, request, response);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(SUCCESSFUL_AUTHENTICATION_MESSAGE);
                    return;
                }
            } catch (AuthenticationException authenticationException) {
                this.securityContextHolderStrategy.clearContext();
                this.logger.debug("Failed to process authentication request", authenticationException);
                throw new WrongCredentialsException();
            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return true;
    }




}
