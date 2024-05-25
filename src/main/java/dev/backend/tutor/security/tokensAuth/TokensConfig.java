package dev.backend.tutor.security.tokensAuth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import dev.backend.tutor.security.tokensAuth.deserializers.AccessTokenDeserializer;
import dev.backend.tutor.security.tokensAuth.deserializers.CookieTokenDeserializer;
import dev.backend.tutor.security.tokensAuth.deserializers.RefreshTokenDeserializer;
import dev.backend.tutor.security.tokensAuth.serializers.AccessTokenSerializer;
import dev.backend.tutor.security.tokensAuth.serializers.CookieTokenSerializer;
import dev.backend.tutor.security.tokensAuth.serializers.RefreshTokenSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;

@Configuration
public class TokensConfig {

    @Value("${jwt.access-token-key}") String accessTokenKey;
    @Value("${jwt.refresh-token-key}") String refreshTokenKey;
    @Value("${jwt.cookie-token-key}") String cookieTokenKey;


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
    public CookieTokenDeserializer cookieTokenDeserializer() throws KeyLengthException, ParseException {
        return new CookieTokenDeserializer(
                new DirectDecrypter(OctetSequenceKey.parse(cookieTokenKey))
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

    @Bean
    public CookieTokenSerializer cookieTokenSerializer() throws KeyLengthException, ParseException {
        return new CookieTokenSerializer(
                new DirectEncrypter(OctetSequenceKey.parse(cookieTokenKey))
        );
    }


}
