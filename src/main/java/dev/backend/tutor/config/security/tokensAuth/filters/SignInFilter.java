package dev.backend.tutor.config.security.tokensAuth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.config.security.tokensAuth.deserializers.TokenSerializer;
import dev.backend.tutor.config.security.tokensAuth.factories.AccessTokenFactory;
import dev.backend.tutor.config.security.tokensAuth.factories.RefreshTokenFactory;
import dev.backend.tutor.config.security.tokensAuth.serializers.AccessTokenSerializer;
import dev.backend.tutor.config.security.tokensAuth.serializers.RefreshTokenSerializer;
import dev.backend.tutor.config.security.tokensAuth.tokens.Tokens;
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

    private static final String USERNAME = "usernameOrEmail";
    private static final String PASSWORD = "password";

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/v2/authentication/login", HttpMethod.POST.name());
    private final SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    private final RefreshTokenFactory refreshTokenFactory;

    private final AccessTokenFactory accessTokenFactory;

    private final TokenSerializer refreshTokenStringSerializer;

    private final TokenSerializer accessTokenStringSerializer;

    private final ObjectMapper objectMapper;

    private final AuthenticationManager authenticationManager;


    public SignInFilter(RefreshTokenFactory refreshTokenFactory, AccessTokenFactory accessTokenFactory, TokenSerializer refreshTokenStringSerializer, TokenSerializer accessTokenStringSerializer, ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        this.refreshTokenFactory = refreshTokenFactory;
        this.accessTokenFactory = accessTokenFactory;
        this.refreshTokenStringSerializer = refreshTokenStringSerializer;
        this.accessTokenStringSerializer = accessTokenStringSerializer;
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (this.requestMatcher.matches(request)) {
            UsernamePasswordAuthenticationToken authenticationToken = convertRequestBodyToAuthenticationToken(request);
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
                        new Tokens(this.accessTokenStringSerializer.serialize(accessToken),
                                accessToken.expiresAt().toString(),
                                this.refreshTokenStringSerializer.serialize(refreshToken),
                                refreshToken.expiresAt().toString()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken convertRequestBodyToAuthenticationToken(HttpServletRequest request) throws BadJsonBodyException, IOException {
        // fixme when throws IOException
        var jsonRequest = this.objectMapper.readTree(request.getReader());
        var username = jsonRequest.get(USERNAME).asText();
        var password = jsonRequest.get(PASSWORD).asText();
        if (username != null && password != null) {
            return new UsernamePasswordAuthenticationToken(username, password);
        } else {
            throw new BadJsonBodyException("properties usernameOrEmail and password set bad");
        }
    }

}
