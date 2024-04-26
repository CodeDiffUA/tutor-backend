package dev.backend.tutor.config.security.tokensAuth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.config.security.tokensAuth.deserializers.RefreshTokenDeserializer;
import dev.backend.tutor.config.security.tokensAuth.tokens.TokenUser;
import dev.backend.tutor.entities.auth.Role;
import dev.backend.tutor.repositories.tokens.JwtTokensRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class SignOutFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/v2/authentication/logout", HttpMethod.POST.name());
    private final RefreshTokenDeserializer refreshTokenDeserializer;
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private final ObjectMapper objectMapper;
    private final JwtTokensRepository jwtTokensRepository;

    public SignOutFilter(RefreshTokenDeserializer refreshTokenDeserializer, ObjectMapper objectMapper, JwtTokensRepository jwtTokensRepository) {
        this.refreshTokenDeserializer = refreshTokenDeserializer;
        this.objectMapper = objectMapper;
        this.jwtTokensRepository = jwtTokensRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.requestMatcher.matches(request)) {
            if (this.securityContextRepository.containsContext(request)) {
                var context = this.securityContextRepository.loadDeferredContext(request).get();
                System.out.println("principal - " + context.getAuthentication().getPrincipal());
                if (context != null && context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken &&
                        context.getAuthentication().getPrincipal() instanceof TokenUser user &&
                        context.getAuthentication().getAuthorities()
                                .contains(new SimpleGrantedAuthority(Role.ROLE_LOGOUT.name()))) {
                    System.out.println(user.getAuthorities());
                    jwtTokensRepository.deactivateRefreshToken(user.token());
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    return;
                } else {
                    throw new AccessDeniedException("Invalid authentication principal or authority");
                }
            } else {
                throw new AccessDeniedException("No security context found for the request");
            }
        }
        filterChain.doFilter(request, response);
    }

}
