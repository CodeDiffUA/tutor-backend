package dev.backend.tutor.config.security.tokensAuth.utills;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.exceptions.BadJsonBodyException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestAuthenticationConverter {

    private static final String USERNAME_OR_EMAIL = "usernameOrEmail";
    private static final String PASSWORD = "password";
    private final ObjectMapper objectMapper;

    public RequestAuthenticationConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public UsernamePasswordAuthenticationToken convertRequestBodyToAuthenticationToken(
            HttpServletRequest request
    ) throws BadJsonBodyException, IOException {
        // fixme when throws IOException
        var jsonRequest = this.objectMapper.readTree(request.getReader());
        var username = jsonRequest.get(USERNAME_OR_EMAIL).asText();
        var password = jsonRequest.get(PASSWORD).asText();
        if (username != null && password != null) {
            return new UsernamePasswordAuthenticationToken(username, password);
        } else {
            throw new BadJsonBodyException("properties usernameOrEmail and password set bad");
        }
    }
}
