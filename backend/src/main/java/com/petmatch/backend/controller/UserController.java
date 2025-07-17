package com.petmatch.backend.controller;

import com.petmatch.backend.dto.MeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Nenhum usuário autenticado"));
        }

        var principal = auth.getPrincipal();

        if (principal instanceof UserDetails user) {
            MeResponse body = new MeResponse(
                    user.getUsername(),
                    user.getAuthorities().stream()
                            .map(a -> a.getAuthority())
                            .toArray(String[]::new),
                    "local"
            );
            return ResponseEntity.ok(body);
        }

        if (principal instanceof OAuth2User oauth) {
            MeResponse body = new MeResponse(
                    oauth.getAttribute("email"),
                    new String[]{"USER"},
                    "google"
            );
            return ResponseEntity.ok(body);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Tipo de autenticação desconhecido"));
    }
}


