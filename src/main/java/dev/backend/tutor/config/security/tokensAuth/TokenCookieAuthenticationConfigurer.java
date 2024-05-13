package dev.backend.tutor.config.security.tokensAuth;

import dev.backend.tutor.config.security.tokensAuth.filters.CookieSignInFilter;
import dev.backend.tutor.config.security.tokensAuth.filters.GetCsrfTokenFilter;
import dev.backend.tutor.config.security.userDetails.TokenAuthenticationUserDetailsService;
import dev.backend.tutor.config.security.userDetails.TokenUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenCookieAuthenticationConfigurer
        extends AbstractHttpConfigurer<TokenCookieAuthenticationConfigurer, HttpSecurity> {

    private final CookieSignInFilter cookieSignInFilter;
    private final JdbcTemplate jdbcTemplate;
    private final TokenAuthenticationUserDetailsService tokenAuthenticationUserDetailsService;
    private final TokenCookieAuthenticationConverter tokenCookieAuthenticationConverter;

    public TokenCookieAuthenticationConfigurer(CookieSignInFilter cookieSignInFilter, JdbcTemplate jdbcTemplate, TokenAuthenticationUserDetailsService tokenAuthenticationUserDetailsService, TokenCookieAuthenticationConverter tokenCookieAuthenticationConverter, GetCsrfTokenFilter getCsrfTokenFilter) {
        this.cookieSignInFilter = cookieSignInFilter;
        this.jdbcTemplate = jdbcTemplate;
        this.tokenAuthenticationUserDetailsService = tokenAuthenticationUserDetailsService;
        this.tokenCookieAuthenticationConverter = tokenCookieAuthenticationConverter;
    }

    @Override
    public void init(HttpSecurity builder) throws Exception {
        builder.logout(logout -> logout.addLogoutHandler(
                        new CookieClearingLogoutHandler("__Host-auth-token"))
                .addLogoutHandler((request, response, authentication) -> {
                    if (authentication != null &&
                            authentication.getPrincipal() instanceof TokenUser user) {
                        this.jdbcTemplate.update("insert into t_deactivated_token (id, c_keep_until) values (?, ?)",
                                user.token().id(), Date.from(user.token().expiresAt()));

                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                }));
    }

    @Override
    public void configure(HttpSecurity builder) {
        var cookieAuthenticationFilter = new AuthenticationFilter(
                builder.getSharedObject(AuthenticationManager.class), tokenCookieAuthenticationConverter);
        cookieAuthenticationFilter.setSuccessHandler((request, response, authentication) -> {});
        cookieAuthenticationFilter.setFailureHandler(
                new AuthenticationEntryPointFailureHandler(
                        new Http403ForbiddenEntryPoint()
                )
        );

        var authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(tokenAuthenticationUserDetailsService);

        builder.addFilterAfter(cookieAuthenticationFilter, CsrfFilter.class)
                .addFilterAfter(cookieSignInFilter, ExceptionTranslationFilter.class)
                .authenticationProvider(authenticationProvider);
    }

}