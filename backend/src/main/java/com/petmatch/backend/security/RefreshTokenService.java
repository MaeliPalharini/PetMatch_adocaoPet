package com.petmatch.backend.security;

import com.petmatch.backend.exception.ResourceNotFoundException;
import com.petmatch.backend.model.RefreshToken;
import com.petmatch.backend.model.User;
import com.petmatch.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repo;

    public RefreshToken create(User user) {
        // revoga tokens antigos para este user
        repo.deleteByUserId(user.getId());

        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiry(Instant.now().plus(7, ChronoUnit.DAYS));
        return repo.save(rt);
    }

    public User validate(String token) {
        RefreshToken rt = repo.findByTokenAndRevokedAtIsNull(token)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token inválido"));

        if (rt.getExpiry().isBefore(Instant.now())) {
            throw new ResourceNotFoundException("Refresh token expirado");
        }

        return rt.getUser();
    }

    public void revoke(String token) {
        repo.findByTokenAndRevokedAtIsNull(token).ifPresent(rt -> {
            rt.setRevokedAt(Instant.now());
            repo.save(rt);
        });
    }

    // Limpeza diária de tokens expirados ou revogados há mais de 30 dias
    @Scheduled(cron = "0 0 0 * * *")
    public void purgeExpiredAndRevoked() {
        Instant cutoff = Instant.now().minus(30, ChronoUnit.DAYS);
        repo.findAll().stream()
                .filter(rt -> rt.getExpiry().isBefore(Instant.now())
                        || (rt.getRevokedAt() != null && rt.getRevokedAt().isBefore(cutoff)))
                .forEach(repo::delete);
    }
}
