package dev.backend.tutor.config.security;


import dev.backend.tutor.sevices.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig implements WebMvcConfigurer {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowCredentials(true);
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(
//            HttpSecurity http,
//            TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer,
//            TokenCookieSessionAuthenticationStrategy tokenCookieSessionAuthenticationStrategy) throws Exception {
//
//        http.apply(tokenCookieAuthenticationConfigurer);
//
//        http
//                .authorizeHttpRequests(authorizeHttpRequests ->
//                        authorizeHttpRequests
//                                .anyRequest().permitAll())
//                .sessionManagement(sessionManagement -> sessionManagement
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .sessionAuthenticationStrategy(tokenCookieSessionAuthenticationStrategy))
//                .csrf(csrf -> csrf.csrfTokenRepository(new CookieCsrfTokenRepository())
//                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//                .sessionAuthenticationStrategy((authentication, request, response) -> {
//                }));
//
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(
//            HttpSecurity http,
//            TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer,
//            TokenCookieSessionAuthenticationStrategy tokenCookieSessionAuthenticationStrategy
//    ) throws Exception {
//        http
//                .apply(tokenCookieAuthenticationConfigurer);
//
//        http
//                .authorizeHttpRequests(authrs -> {
//                    authrs.requestMatchers("/api/v1/authentication/**").permitAll();
//                    authrs.requestMatchers("/api/v1/registration/**").permitAll();
//                    authrs.anyRequest().permitAll();
//                })
//                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                        .sessionAuthenticationStrategy(tokenCookieSessionAuthenticationStrategy))
//                .csrf(csrf -> csrf.csrfTokenRepository(new CookieCsrfTokenRepository())
//                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//                        .sessionAuthenticationStrategy((authentication, request, response) -> {
//                        }));
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(
//            HttpSecurity http,
//            TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer,
//            TokenCookieSessionAuthenticationStrategy tokenCookieSessionAuthenticationStrategy,
//            JwtAuthenticationConfigurer jwtAuthenticationConfigurer) throws Exception {
//
//        http
//                .apply(tokenCookieAuthenticationConfigurer);
//        http
//                .apply(jwtAuthenticationConfigurer);
//
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests( authrs -> {
//                    authrs.requestMatchers("/api/v1/authentication/**").permitAll();
//                    authrs.requestMatchers("/api/v1/registration/**").permitAll();
//                    authrs.anyRequest().permitAll();
//                })
//                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                        .sessionAuthenticationStrategy(tokenCookieSessionAuthenticationStrategy));
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtFilter jwtFilter
            ) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authrs -> {
                    authrs.requestMatchers("/api/v2/authentication/login").permitAll();
                    authrs.anyRequest().permitAll();
                })
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling.authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
