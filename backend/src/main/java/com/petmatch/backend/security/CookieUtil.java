package com.petmatch.backend.security;

import com.petmatch.backend.config.CookieProps;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieUtil {

    private final CookieProps cfg;

    // access-token (15 min)
    public void addAuthCookie(HttpServletResponse res, String jwt) {
        String header = "token=%s; Path=/api; HttpOnly; SameSite=Strict; %s; Max-Age=%d"
                .formatted(
                        jwt,
                        cfg.isSecure() ? "Secure" : "",
                        15 * 60
                );
        res.addHeader("Set-Cookie", header);
    }

    // refresh-token (7 dias)
    public void addRefreshCookie(HttpServletResponse res, String rt) {
        String header = "refreshToken=%s; Path=/api/auth/refresh; HttpOnly; SameSite=None; %s; Max-Age=%d"
                .formatted(
                        rt,
                        cfg.isSecure() ? "Secure" : "",
                        7 * 24 * 60 * 60
                );
        res.addHeader("Set-Cookie", header);
    }

    //  expiradores
    public void expireAuthCookie(HttpServletResponse res) {
        res.addHeader("Set-Cookie",
                "token=; Path=/api; Max-Age=0; HttpOnly; SameSite=Strict; %s"
                        .formatted(cfg.isSecure() ? "Secure" : "")
        );
    }

    public void expireRefreshCookie(HttpServletResponse res) {
        res.addHeader("Set-Cookie",
                "refreshToken=; Path=/api/auth/refresh; Max-Age=0; HttpOnly; SameSite=None; %s"
                        .formatted(cfg.isSecure() ? "Secure" : "")
        );
    }
}
