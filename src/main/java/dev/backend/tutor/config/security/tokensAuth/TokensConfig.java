package dev.backend.tutor.config.security.tokensAuth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import dev.backend.tutor.config.security.tokensAuth.deserializers.AccessTokenDeserializer;
import dev.backend.tutor.config.security.tokensAuth.deserializers.RefreshTokenDeserializer;
import dev.backend.tutor.config.security.tokensAuth.serializers.AccessTokenSerializer;
import dev.backend.tutor.config.security.tokensAuth.serializers.RefreshTokenSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;

@Configuration
public class TokensConfig {

    @Value("${jwt.access-token-key}") String accessTokenKey;
    @Value("${jwt.refresh-token-key}") String refreshTokenKey;


    @Bean
    public RefreshTokenDeserializer refreshTokenStringDeserializer() throws KeyLengthException, ParseException {
        return new RefreshTokenDeserializer(
                new DirectDecrypter(OctetSequenceKey.parse(refreshTokenKey))
        );
    }

    @Bean
    public AccessTokenDeserializer accessTokenStringDeserializer() throws JOSEException, ParseException {
        return new AccessTokenDeserializer(
                new MACVerifier(OctetSequenceKey.parse(accessTokenKey))
        );
    }

    @Bean
    public RefreshTokenSerializer refreshTokenStringSerializer() throws KeyLengthException, ParseException {
        return new RefreshTokenSerializer(
                new DirectEncrypter(OctetSequenceKey.parse(refreshTokenKey))
        );
    }

    @Bean
    public AccessTokenSerializer accessTokenStringSerializer() throws KeyLengthException, ParseException {
        return new AccessTokenSerializer(
                new MACSigner(OctetSequenceKey.parse(accessTokenKey))
        );
    }

}
