package dev.simoncodes.todosync.service;

import dev.simoncodes.todosync.entity.RefreshToken;
import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepo;

    public RefreshToken createRefreshToken(String token, User user, Instant expiresOn) {
        RefreshToken rt = new RefreshToken();
        rt.setToken(token);
        rt.setUser(user);
        rt.setExpiresOn(expiresOn);
        rt.setRevoked(false);
        return refreshTokenRepo.save(rt);
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken rt = refreshTokenRepo.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Refresh token string provided not found in token store."));
        if (rt.getRevoked()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token provided has been revoked");
        }
        if (rt.getExpiresOn().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token provided has expired.");
        }
        return rt;
    }

    public void revokeToken(String token) {
        RefreshToken rt = refreshTokenRepo.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Refresh token string provided not found in token store."));
        rt.setRevoked(true);
        refreshTokenRepo.save(rt);
    }

    public void revokeAllUserTokens(UUID userId) {
        List<RefreshToken> rts = refreshTokenRepo.findAllByUserId(userId);
        if (rts.isEmpty()) return;
        rts.forEach(rt -> rt.setRevoked(true));
        refreshTokenRepo.saveAll(rts);
    }
}
