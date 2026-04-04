package dev.simoncodes.todosync.auth;

import dev.simoncodes.todosync.auth.dto.RefreshRequestDto;
import dev.simoncodes.todosync.config.JwtProperties;
import dev.simoncodes.todosync.auth.dto.LoginRequestDto;
import dev.simoncodes.todosync.auth.dto.LoginResponseDto;
import dev.simoncodes.todosync.entity.RefreshToken;
import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProps;

    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepo.findByEmail(request.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        boolean verified = encoder.matches(request.password(), user.getPasswordHash());
        if (!verified) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        refreshTokenService.revokeAllUserTokens(user.getId());
        return generateLoginResponse(user);
    }

    public LoginResponseDto refresh(RefreshRequestDto request) {
        RefreshToken rt = refreshTokenService.validateRefreshToken(request.refreshToken());
        LoginResponseDto response = generateLoginResponse(rt.getUser());
        refreshTokenService.revokeToken(request.refreshToken());
        return response;
    }

    private LoginResponseDto generateLoginResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user.getId());
        String refreshToken = jwtService.generateRefreshToken(user.getId());
        Instant refreshTokenExpiry = jwtService.extractClaims(refreshToken).getExpiration().toInstant();
        RefreshToken rt = refreshTokenService.createRefreshToken(refreshToken, user, refreshTokenExpiry);

        return new LoginResponseDto(
                accessToken,
                rt.getToken(),
                "Bearer",
                jwtProps.accessTokenExpiry() / 1000
        );
    }
}