package com.petmatch.backend.security;

import com.petmatch.backend.dto.LoginRequest;
import com.petmatch.backend.dto.LoginResponse;
import com.petmatch.backend.dto.RegisterRequest;
import com.petmatch.backend.model.RefreshToken;
import com.petmatch.backend.model.Role;
import com.petmatch.backend.model.User;
import com.petmatch.backend.repository.UserRepository;
import com.petmatch.backend.security.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final CookieUtil cookieUtil;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            RefreshTokenService refreshTokenService,
            CookieUtil cookieUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.cookieUtil = cookieUtil;
    }

    public void register(RegisterRequest req, HttpServletResponse res) {
        if (userRepository.existsByEmail(req.userEmail())) {
            throw new IllegalStateException("E-mail já cadastrado");
        }
        User user = User.builder()
                .name(req.name())
                .email(req.userEmail())
                .password(passwordEncoder.encode(req.password()))
                .role(Role.fromString(req.role()))
                .build();
        userRepository.save(user);

        String access = jwtService.gerarToken(user.getEmail());
        cookieUtil.addAuthCookie(res, access);
        RefreshToken rt = refreshTokenService.create(user);
        cookieUtil.addRefreshCookie(res, rt.getToken());
    }

    public LoginResponse login(LoginRequest req, HttpServletResponse res) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.userEmail(), req.password())
        );
        User user = userRepository.findByEmail(req.userEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String access = jwtService.gerarToken(user.getEmail());
        cookieUtil.addAuthCookie(res, access);
        RefreshToken rt = refreshTokenService.create(user);
        cookieUtil.addRefreshCookie(res, rt.getToken());
        return new LoginResponse(access);
    }
}
