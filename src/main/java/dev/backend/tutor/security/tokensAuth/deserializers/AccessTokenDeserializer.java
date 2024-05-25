package dev.backend.tutor.security.tokensAuth.deserializers;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import dev.backend.tutor.security.tokensAuth.serializers.TokenDeserializer;
import dev.backend.tutor.security.tokensAuth.tokens.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.UUID;


public class AccessTokenDeserializer implements TokenDeserializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenDeserializer.class);

    private final JWSVerifier jwsVerifier;

    public AccessTokenDeserializer(JWSVerifier jwsVerifier) {
        this.jwsVerifier = jwsVerifier;
    }

    @Override
    public AccessToken deserialize(String string) {
        try {
            var signedJWT = SignedJWT.parse(string);
            if (signedJWT.verify(this.jwsVerifier)) {
                var claimsSet = signedJWT.getJWTClaimsSet();
                return new AccessToken(UUID.fromString(claimsSet.getJWTID()), claimsSet.getSubject(),
                        claimsSet.getStringListClaim("authorities"),
                        claimsSet.getIssueTime().toInstant(),
                        claimsSet.getExpirationTime().toInstant());
            }
        } catch (ParseException | JOSEException exception) {
            LOGGER.error("Could not deserialize accessToken");
        }
        return null;
    }
}
