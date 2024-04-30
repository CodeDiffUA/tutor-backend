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
        var cookie = new Cookie("__Host-refresh-token", jwtAndRefreshDto.refreshToken());
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "lax");
        cookie.setMaxAge(REFRESH_COOKIE_LIVE_TERM_SECONDS);
        httpServletResponse.setContentType("text/plain");
        return cookie;
    }

    public Cookie getRefreshTokenCookie(HttpServletRequest httpServletRequest) throws CookieException {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .orElseThrow(() -> new CookieException("no refresh cookie"));
    }
}
