package dev.backend.tutor.security.tokensAuth.serializers;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.backend.tutor.security.tokensAuth.deserializers.TokenSerializer;
import dev.backend.tutor.security.tokensAuth.tokens.Token;

import java.util.Date;

public class AccessTokenSerializer implements TokenSerializer {

    private final JWSSigner jwsSigner;

    private JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;


    public AccessTokenSerializer(JWSSigner jwsSigner, JWSAlgorithm jwsAlgorithm) {
        this.jwsSigner = jwsSigner;
        this.jwsAlgorithm = jwsAlgorithm;
    }

    public AccessTokenSerializer(JWSSigner jwsSigner) {
        this.jwsSigner = jwsSigner;
    }

    @Override
    public String serialize(Token accessToken) {
        var jwsHeader = new JWSHeader.Builder(this.jwsAlgorithm)
                .keyID(accessToken.id().toString())
                .build();
        var claimsSet = new JWTClaimsSet.Builder()
                .jwtID(accessToken.id().toString())
                .subject(accessToken.subject())
                .issueTime(Date.from(accessToken.createdAt()))
                .expirationTime(Date.from(accessToken.expiresAt()))
                .claim("authorities", accessToken.authorities())
                .build();
        var signedJWT = new SignedJWT(jwsHeader, claimsSet);
        try {
            signedJWT.sign(this.jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }

    public void setJwsAlgorithm(JWSAlgorithm jwsAlgorithm) {
        this.jwsAlgorithm = jwsAlgorithm;
    }
}
