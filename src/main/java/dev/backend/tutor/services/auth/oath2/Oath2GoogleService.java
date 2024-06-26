package dev.backend.tutor.services.auth.oath2;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.BannedException;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.sql.student.StudentRepository;
import dev.backend.tutor.services.security.TokenFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class Oath2GoogleService {

    @Value("${spring.security.oauth2.resource-server.opaque-token.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.resource-server.opaque-token.client-secret}")
    private String clientSecret;

    private static final String REDIRECT_URI_DEV = "http://localhost:8080/api/v1/authentication/callback";
    private static final String REDIRECT_URI_PROD = "https://tutor-backend-k28m.onrender.com/api/v1/authentication/callback";
    private final OpaqueTokenIntrospector opaqueTokenIntrospector;
    private final TokenFactory tokenFactory;
    private final StudentRepository studentRepository;

    public Oath2GoogleService(OpaqueTokenIntrospector opaqueTokenIntrospector, TokenFactory tokenFactory, StudentRepository studentRepository) {
        this.opaqueTokenIntrospector = opaqueTokenIntrospector;
        this.tokenFactory = tokenFactory;
        this.studentRepository = studentRepository;
    }

    public String getGoogleAuthorizationRedirectUrl() {
        return new GoogleAuthorizationCodeRequestUrl(
                clientId,
                REDIRECT_URI_PROD,
                Arrays.asList("email", "profile", "openid")
        ).build();
    }

    public JwtAndRefreshDto authorizeStudent(String code) throws IOException, NotFoundUserException, BannedException {
        String token = getAccessTokenFromRequest(code);
        String email = getEmailFromToken(token);
        Student student = getStudentWithRolesByEmail(email);
        return generateJwtAndRefreshTokenFromUserDetails(student);
    }

    private void checkIfStudentHasAccess(Student student) throws BannedException {
        if (student.isNotActivated()) {
            throw new NotConfirmedEmailException("User has not confirmed email yet!");
        } // todo: remove it when presentation is
        if (student.isBanned()) {
            throw new BannedException("User with email " + student.getEmail() + " is banned");
        } // todo: remove it when presentation is
    }


    private JwtAndRefreshDto generateJwtAndRefreshTokenFromUserDetails(Student student) throws NotFoundUserException {
        String jwt = tokenFactory.createJwt(student);
        RefreshToken refreshToken = tokenFactory.createRefreshToken(student);
        return new JwtAndRefreshDto(jwt, refreshToken.getToken());
    }

    private Student getStudentWithRolesByEmail(String email) throws NotFoundUserException, BannedException {
        var student = studentRepository.findStudentsByUsernameOrEmailWithRoles(email).orElseThrow(
                () -> new NotFoundUserException("not found user -> " + email + ". You need to sign up")
        );
        checkIfStudentHasAccess(student); // todo: remove it when presentation is
        return student;
    }

    private String getEmailFromToken(String token) {
        OAuth2AuthenticatedPrincipal principal = opaqueTokenIntrospector.introspect(token);
        return (String) principal.getAttributes().get("email");
    }

    private String getAccessTokenFromRequest(String code) throws IOException {
        return new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(), new GsonFactory(),
                clientId,
                clientSecret,
                code,
                REDIRECT_URI_PROD
        ).execute().getAccessToken();
    }
}
