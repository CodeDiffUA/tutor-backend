package dev.backend.tutor.security.tokensAuth.deserializers;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import dev.backend.tutor.security.tokensAuth.serializers.TokenDeserializer;
import dev.backend.tutor.security.tokensAuth.tokens.CookieToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.UUID;

public class CookieTokenDeserializer implements TokenDeserializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieTokenDeserializer.class);

    private final JWEDecrypter jweDecrypter;

    public CookieTokenDeserializer(JWEDecrypter jweDecrypter) {
        this.jweDecrypter = jweDecrypter;
    }

    @Override
    public CookieToken deserialize(String string) {
        try {
            var encryptedJWT = EncryptedJWT.parse(string);
            encryptedJWT.decrypt(this.jweDecrypter);
            var claimsSet = encryptedJWT.getJWTClaimsSet();
            return new CookieToken(UUID.fromString(claimsSet.getJWTID()), claimsSet.getSubject(),
                    claimsSet.getStringListClaim("authorities"),
                    claimsSet.getIssueTime().toInstant(),
                    claimsSet.getExpirationTime().toInstant());
        } catch (ParseException | JOSEException exception) {
            LOGGER.error("Could not deserialize refreshToken");
        }

        return null;
    }
}
