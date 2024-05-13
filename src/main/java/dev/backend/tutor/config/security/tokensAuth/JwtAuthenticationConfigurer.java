package dev.backend.tutor.config.security.tokensAuth;

import dev.backend.tutor.config.security.tokensAuth.filters.RefreshTokenFilter;
import dev.backend.tutor.config.security.tokensAuth.filters.SignInFilter;
import dev.backend.tutor.config.security.tokensAuth.filters.SignOutFilter;
import dev.backend.tutor.config.security.userDetails.TokenAuthenticationUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationConfigurer
        extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {

    private final RefreshTokenFilter refreshTokenFilter;
    private final SignInFilter signInFilter;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final SignOutFilter signOutFilter;
    private final TokenAuthenticationUserDetailsService tokenAuthenticationUserDetailsService;

    public JwtAuthenticationConfigurer(RefreshTokenFilter refreshTokenFilter, SignInFilter signInFilter, JwtAuthenticationConverter jwtAuthenticationConverter, SignOutFilter signOutFilter, TokenAuthenticationUserDetailsService tokenAuthenticationUserDetailsService) {
        this.refreshTokenFilter = refreshTokenFilter;
        this.signInFilter = signInFilter;
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
        this.signOutFilter = signOutFilter;
        this.tokenAuthenticationUserDetailsService = tokenAuthenticationUserDetailsService;
    }

    @Override
    public void init(HttpSecurity builder) {
        var csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if (csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher("/", "POST"));
        }
    }

    @Override
    public void configure(HttpSecurity builder) {
        var authFilter = buildJwtParserFromHeaderFilterConverter(builder);
        authFilter.setSuccessHandler((request, response, authentication) -> CsrfFilter.skipRequest(request));
        authFilter.setFailureHandler(new AuthenticationEntryPointFailureHandler(new Http403ForbiddenEntryPoint()));

        var authenticationProvider = buildCustomAuthenticationProvider();

        builder.addFilterAfter(signInFilter, ExceptionTranslationFilter.class)
                .addFilterBefore(authFilter, CsrfFilter.class)
                .addFilterAfter(refreshTokenFilter, ExceptionTranslationFilter.class)
                .addFilterAfter(signOutFilter, ExceptionTranslationFilter.class)
                .authenticationProvider(authenticationProvider);
    }

    private PreAuthenticatedAuthenticationProvider buildCustomAuthenticationProvider() {
        var authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(tokenAuthenticationUserDetailsService);
        return authenticationProvider;
    }

    private AuthenticationFilter buildJwtParserFromHeaderFilterConverter(HttpSecurity builder) {
        return new AuthenticationFilter(
                builder.getSharedObject(AuthenticationManager.class),
                jwtAuthenticationConverter);
    }
}
