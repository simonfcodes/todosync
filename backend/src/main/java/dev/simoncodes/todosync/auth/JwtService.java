package dev.simoncodes.todosync.auth;

import dev.simoncodes.todosync.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtProperties properties;
    private SecretKey signingKey;

    public String generateAccessToken(UUID userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + properties.accessTokenExpiry()))
                .signWith(signingKey)
                .compact();
    }

    public String generateRefreshToken(UUID userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + properties.refreshTokenExpiry()))
                .signWith(signingKey)
                .compact();
        // Future method will take the token and save it in token repo before returning
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @PostConstruct
    private void initSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.secret());
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }
}
