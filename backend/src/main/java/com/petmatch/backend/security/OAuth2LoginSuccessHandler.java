package com.petmatch.backend.security;

import com.petmatch.backend.model.RefreshToken;
import com.petmatch.backend.model.Role;
import com.petmatch.backend.model.User;
import com.petmatch.backend.repository.UserRepository;
import com.petmatch.backend.security.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CookieUtil cookieUtil;
    private final UserRepository userRepository;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Extrai o e-mail do usuário autenticado via OAuth2
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String userEmail = oauth2User.getAttribute("email");


        User user = userRepository.findByEmail(userEmail)
                .orElseGet(() -> {
                    User novo = User.builder()
                            .name(oauth2User.getAttribute("name"))
                            .email(userEmail)

                            .password("OAUTH2_USER")
                            .role(Role.ADOTANTE)
                            .build();
                    return userRepository.save(novo);
                });

        // Gera Access Token (JWT)
        String accessToken = jwtService.gerarToken(userEmail);
        cookieUtil.addAuthCookie(response, accessToken);

        // Gera e persiste Refresh Token
        RefreshToken rt = refreshTokenService.create(user);
        cookieUtil.addRefreshCookie(response, rt.getToken());

        // Finalmente redireciona para o front-end
        response.sendRedirect(frontendUrl);
    }
}


