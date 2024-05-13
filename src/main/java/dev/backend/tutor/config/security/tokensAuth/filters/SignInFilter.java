package dev.backend.tutor.config.security.tokensAuth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.config.security.tokensAuth.factories.AccessTokenFactory;
import dev.backend.tutor.config.security.tokensAuth.factories.CookieTokenFactory;
import dev.backend.tutor.config.security.tokensAuth.factories.RefreshTokenFactory;
import dev.backend.tutor.config.security.tokensAuth.serializers.AccessTokenSerializer;
import dev.backend.tutor.config.security.tokensAuth.serializers.CookieTokenSerializer;
import dev.backend.tutor.config.security.tokensAuth.serializers.RefreshTokenSerializer;
import dev.backend.tutor.config.security.tokensAuth.utills.RequestAuthenticationConverter;
import dev.backend.tutor.dtos.auth.TokensDto;
import dev.backend.tutor.exceptions.BadJsonBodyException;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SignInFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/native/v2/authentication/login", HttpMethod.POST.name());
    private final SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    private final RequestAuthenticationConverter requestAuthenticationConverter;
    private final RefreshTokenSerializer refreshTokenSerializer;
    private final AccessTokenSerializer accessTokenSerializer;
    private final RefreshTokenFactory refreshTokenFactory;
    private final AccessTokenFactory accessTokenFactory;


    public SignInFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager, RequestAuthenticationConverter requestAuthenticationConverter, RefreshTokenSerializer refreshTokenSerializer, AccessTokenSerializer accessTokenSerializer, RefreshTokenFactory refreshTokenFactory, AccessTokenFactory accessTokenFactory) {
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.requestAuthenticationConverter = requestAuthenticationConverter;
        this.refreshTokenSerializer = refreshTokenSerializer;
        this.accessTokenSerializer = accessTokenSerializer;
        this.refreshTokenFactory = refreshTokenFactory;
        this.accessTokenFactory = accessTokenFactory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        this.logger.info("native sign in filter called");
        if (this.requestMatcher.matches(request)) {
            UsernamePasswordAuthenticationToken authenticationToken = requestAuthenticationConverter.convertRequestBodyToAuthenticationToken(request);
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(authenticationToken);
            } catch (AuthenticationException authenticationException) {
                this.securityContextHolderStrategy.clearContext();
                this.logger.debug("Failed to process authentication request", authenticationException);
                throw new WrongCredentialsException();
            }
            if (authentication != null) {
                SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                this.securityContextHolderStrategy.setContext(context);
                this.securityContextRepository.saveContext(context, request, response);
                var refreshToken = this.refreshTokenFactory.createToken(authentication);
                var accessToken = this.accessTokenFactory.createToken(refreshToken);

                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                this.objectMapper.writeValue(response.getWriter(),
                        new TokensDto(this.accessTokenSerializer.serialize(accessToken),
                                accessToken.expiresAt().toString(),
                                this.refreshTokenSerializer.serialize(refreshToken),
                                refreshToken.expiresAt().toString()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return true;
    }




}
