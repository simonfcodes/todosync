package dev.simoncodes.todosync.repository;

import dev.simoncodes.todosync.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findAllByUserId(UUID userId);
    List<RefreshToken> findAllByExpiresOnBefore(Instant time);
}
