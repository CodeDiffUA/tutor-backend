package dev.backend.tutor.sevices.cookie;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.exceptions.CookieException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CookieService {

    private static final Integer REFRESH_COOKIE_LIVE_TERM = 3600 * 24 * 14;


    public Cookie createCookieWithRefreshToken(HttpServletResponse httpServletResponse, JwtAndRefreshDto jwtAndRefreshDto) {
        Cookie cookie = new Cookie("refreshToken", jwtAndRefreshDto.refreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(REFRESH_COOKIE_LIVE_TERM);
        httpServletResponse.setContentType("text/plain");
        httpServletResponse.addCookie(cookie);
        return cookie;
    }

    public Cookie getRefreshTokenCookie(HttpServletRequest httpServletRequest) throws CookieException {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .orElseThrow(() -> new CookieException("no refresh cookie"));
    }
}
