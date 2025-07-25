package com.petmatch.backend.repository;

import com.petmatch.backend.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenAndRevokedAtIsNull(String token);
    void deleteByUserId(Long userId);
}

