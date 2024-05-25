package dev.backend.tutor.security.tokensAuth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.security.tokensAuth.factories.AccessTokenFactory;
import dev.backend.tutor.security.tokensAuth.serializers.AccessTokenSerializer;
import dev.backend.tutor.security.tokensAuth.serializers.RefreshTokenSerializer;
import dev.backend.tutor.security.userDetails.TokenUser;
import dev.backend.tutor.dtos.auth.TokensDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/native/v2/authentication/refresh", HttpMethod.POST.name());
    private final AccessTokenFactory accessTokenFactory;
    private final AccessTokenSerializer accessTokenSerializer;
    private final RefreshTokenSerializer refreshTokenSerializer;
    private final ObjectMapper objectMapper;
    private final SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    public RefreshTokenFilter(AccessTokenFactory accessTokenFactory, AccessTokenSerializer accessTokenSerializer, RefreshTokenSerializer refreshTokenSerializer, ObjectMapper objectMapper) {
        this.accessTokenFactory = accessTokenFactory;
        this.accessTokenSerializer = accessTokenSerializer;
        this.refreshTokenSerializer = refreshTokenSerializer;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.logger.info("refresh token filter called");
        if (this.requestMatcher.matches(request)) {
            var context = this.securityContextRepository.loadDeferredContext(request).get();
            if (this.securityContextRepository.containsContext(request)) {
                System.out.println(context.getAuthentication().getPrincipal());
                if (context != null && context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken &&
                        context.getAuthentication().getPrincipal() instanceof TokenUser user &&
                        context.getAuthentication().getAuthorities()
                                .contains(new SimpleGrantedAuthority("ROLE_JWT_REFRESH"))){
                    var accessToken = this.accessTokenFactory.createToken(user.token());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    this.objectMapper.writeValue(
                            response.getWriter(),
                            new TokensDto(this.accessTokenSerializer.serialize(accessToken),
                                    accessToken.expiresAt().toString(),
                                    refreshTokenSerializer.serialize(user.token()),
                                    user.token().expiresAt().toString())
                    );
                }
            } else {
                throw new AccessDeniedException("This token cant refresh access token");
            }
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return true;
    }


}
