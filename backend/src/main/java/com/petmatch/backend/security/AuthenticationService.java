package com.petmatch.backend.security;
import com.petmatch.backend.dto.LoginRequest;
import com.petmatch.backend.dto.LoginResponse;
import com.petmatch.backend.dto.RegisterRequest;
import com.petmatch.backend.model.Role;
import com.petmatch.backend.model.User;
import com.petmatch.backend.repository.UserRepository;
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

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequest req) {
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
    }

    public LoginResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.userEmail(), req.password())
        );

        User user = userRepository.findByEmail(req.userEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String accessToken = jwtService.gerarToken(user.getEmail());

        return new LoginResponse(accessToken);
    }
}
