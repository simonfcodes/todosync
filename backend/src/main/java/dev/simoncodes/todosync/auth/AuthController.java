package dev.simoncodes.todosync.auth;

import dev.simoncodes.todosync.auth.dto.LoginRequestDto;
import dev.simoncodes.todosync.auth.dto.LoginResponseDto;
import dev.simoncodes.todosync.auth.dto.RefreshRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshUser(@Valid @RequestBody RefreshRequestDto request) {
        LoginResponseDto response = authService.refresh(request);
        return ResponseEntity.ok(response);
    }
}
