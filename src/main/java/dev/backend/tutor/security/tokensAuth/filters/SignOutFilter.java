package dev.backend.tutor.security.tokensAuth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.security.tokensAuth.deserializers.RefreshTokenDeserializer;
import dev.backend.tutor.security.tokensAuth.tokens.Token;
import dev.backend.tutor.security.userDetails.TokenUser;
import dev.backend.tutor.entities.auth.Role;
import dev.backend.tutor.exceptions.BadJsonBodyException;
import dev.backend.tutor.repositories.sql.tokens.JwtTokensRepository;
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

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/native/v2/authentication/logout", HttpMethod.POST.name());
    private final RefreshTokenDeserializer refreshTokenDeserializer;
    private final SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private final ObjectMapper objectMapper;
    private final JwtTokensRepository jwtTokensRepository;

    public SignOutFilter(RefreshTokenDeserializer refreshTokenDeserializer, ObjectMapper objectMapper, JwtTokensRepository jwtTokensRepository) {
        this.refreshTokenDeserializer = refreshTokenDeserializer;
        this.objectMapper = objectMapper;
        this.jwtTokensRepository = jwtTokensRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.logger.info("native sign out filter called");
        if (this.requestMatcher.matches(request)) {
            if (this.securityContextRepository.containsContext(request)) {
                var context = this.securityContextRepository.loadDeferredContext(request).get();
                System.out.println("principal - " + context.getAuthentication().getPrincipal());
                if (context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken &&
                        context.getAuthentication().getPrincipal() instanceof TokenUser user &&
                        context.getAuthentication().getAuthorities()
                                .contains(new SimpleGrantedAuthority(Role.ROLE_LOGOUT.name()))) {
                    System.out.println(user.getAuthorities());
                    String refreshTokenString = getRefreshTokenFromRequestBody(request);
                    Token refreshToken = refreshTokenDeserializer.deserialize(refreshTokenString);
                    Token accessToken = user.token();
                    jwtTokensRepository.deactivateRefreshToken(refreshToken);
                    jwtTokensRepository.deactivateRefreshToken(accessToken);
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

    private String getRefreshTokenFromRequestBody(
            HttpServletRequest request) throws BadJsonBodyException, IOException {
        var jsonRequest = this.objectMapper.readTree(request.getReader());
        var accessToken = jsonRequest.get(REFRESH_TOKEN).asText();
        if (accessToken != null) {
            return accessToken;
        } else {
            throw new BadJsonBodyException("could not find refreshToken property in json body");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return true;
    }



}
