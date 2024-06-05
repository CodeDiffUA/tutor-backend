package dev.backend.tutor.services.cookie;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.exceptions.CookieException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Service
public class CookieService {

    private static final int REFRESH_COOKIE_LIVE_TERM_SECONDS = Duration.ofDays(14L).toMillisPart();

    public Cookie createSecureHttpOnlyTokenCookie(
            String cookieName, String cookieValue, Instant expiry)
    {
        var cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(), expiry));
        return cookie;
    }

    public Cookie getCookieByName(HttpServletRequest httpServletRequest, String cookieName) throws CookieException {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .orElseThrow(() -> new CookieException("no cookie " + cookieName));
    }
  
    @Deprecated
    public Cookie createCookieWithRefreshToken(HttpServletResponse httpServletResponse, JwtAndRefreshDto jwtAndRefreshDto) {
        var cookie = new Cookie("__Host-refresh-token", jwtAndRefreshDto.refreshToken());
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite","none"); // Directly setting the SameSite attribute


        cookie.setMaxAge(3600*24*14);
        httpServletResponse.setContentType("text/plain");
        httpServletResponse.addCookie(cookie);
        return cookie;
    }

    @Deprecated
    public Cookie getRefreshTokenCookie(HttpServletRequest httpServletRequest) throws CookieException {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("__Host-refresh"))
                .findFirst()
                .orElseThrow(() -> new CookieException("no refresh cookie"));
    }
}
