package dev.backend.tutor.security.googleAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class GoogleAuthConfig {

    private final WebClient userInfoClient;

    public GoogleAuthConfig(WebClient userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    @Bean
    public OpaqueTokenIntrospector introspector() {
        return new GoogleOpaqueTokenIntrospector(userInfoClient);
    }


}
