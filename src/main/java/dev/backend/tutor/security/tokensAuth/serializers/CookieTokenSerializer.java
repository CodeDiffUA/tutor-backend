package dev.backend.tutor.security.tokensAuth.serializers;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import dev.backend.tutor.security.tokensAuth.deserializers.TokenSerializer;
import dev.backend.tutor.security.tokensAuth.tokens.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class CookieTokenSerializer implements TokenSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieTokenSerializer.class);

    private final JWEEncrypter jweEncrypter;

    private JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;

    private EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    public CookieTokenSerializer(JWEEncrypter jweEncrypter) {
        this.jweEncrypter = jweEncrypter;
    }

    public CookieTokenSerializer(JWEEncrypter jweEncrypter, JWEAlgorithm jweAlgorithm, EncryptionMethod encryptionMethod) {
        this.jweEncrypter = jweEncrypter;
        this.jweAlgorithm = jweAlgorithm;
        this.encryptionMethod = encryptionMethod;
    }

    @Override
    public String serialize(Token accessToken) {
        var jwsHeader = new JWEHeader.Builder(this.jweAlgorithm, this.encryptionMethod)
                .keyID(accessToken.id().toString())
                .build();
        var claimsSet = new JWTClaimsSet.Builder()
                .jwtID(accessToken.id().toString())
                .subject(accessToken.subject())
                .issueTime(Date.from(accessToken.createdAt()))
                .expirationTime(Date.from(accessToken.expiresAt()))
                .claim("authorities", accessToken.authorities())
                .build();
        var encryptedJWT = new EncryptedJWT(jwsHeader, claimsSet);
        try {
            encryptedJWT.encrypt(this.jweEncrypter);

            return encryptedJWT.serialize();
        } catch (JOSEException exception) {
            LOGGER.error(exception.getMessage(), exception);
        }

        return null;
    }

    public void setJweAlgorithm(JWEAlgorithm jweAlgorithm) {
        this.jweAlgorithm = jweAlgorithm;
    }

    public void setEncryptionMethod(EncryptionMethod encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
    }


}