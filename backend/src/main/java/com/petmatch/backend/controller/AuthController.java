package com.petmatch.backend.controller;

import com.petmatch.backend.dto.*;
import com.petmatch.backend.security.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService   refreshTokenService;
    private final JwtService            jwtService;
    private final CookieUtil            cookieUtil;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        authenticationService.register(request, response);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authenticationService.login(request, response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        var user       = refreshTokenService.validate(refreshToken);
        var newAccess  = jwtService.gerarToken(user.getEmail());
        refreshTokenService.revoke(refreshToken);
        var newRefresh = refreshTokenService.create(user).getToken();
        cookieUtil.addAuthCookie   (response, newAccess);
        cookieUtil.addRefreshCookie(response, newRefresh);
        return ResponseEntity.ok(new LoginResponse(newAccess));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        refreshTokenService.revoke(refreshToken);
        cookieUtil.expireAuthCookie   (response);
        cookieUtil.expireRefreshCookie(response);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuário não autenticado"));
        }
        var principal = auth.getPrincipal();
        if (principal instanceof UserDetails usr) {
            return ResponseEntity.ok(new MeResponse(
                    usr.getUsername(),
                    usr.getAuthorities().stream()
                            .map(a -> a.getAuthority()).toArray(String[]::new),
                    "local"
            ));
        }
        if (principal instanceof OAuth2User oauth) {
            return ResponseEntity.ok(new MeResponse(
                    oauth.getAttribute("email"),
                    new String[]{"USER"},
                    "google"
            ));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Tipo de autenticação desconhecido"));
    }
}
