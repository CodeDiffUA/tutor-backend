package dev.backend.tutor.sevices.cookie;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.exceptions.CookieException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class CookieService {

    private static final int REFRESH_COOKIE_LIVE_TERM_SECONDS =
            (int) ChronoUnit.SECONDS.between(
                    Instant.now(),
                    Instant.now().plusSeconds(TimeUnit.DAYS.toSeconds(14)));
    public Cookie createCookieWithRefreshToken(HttpServletResponse httpServletResponse, JwtAndRefreshDto jwtAndRefreshDto) {
        Cookie cookie = new Cookie("__Host-refresh", jwtAndRefreshDto.refreshToken());
        cookie.setHttpOnly(true);
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");

        cookie.setPath("/");
        cookie.setMaxAge(REFRESH_COOKIE_LIVE_TERM_SECONDS);
        httpServletResponse.setContentType("text/plain");
        httpServletResponse.addCookie(cookie);
        return cookie;
    }

    public Cookie getRefreshTokenCookie(HttpServletRequest httpServletRequest) throws CookieException {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("__Host-refresh"))
                .findFirst()
                .orElseThrow(() -> new CookieException("no refresh cookie"));
    }
}
