package dev.simoncodes.todosync.user;

import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.user.dto.UserRegisterRequestDto;
import dev.simoncodes.todosync.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticatedUserService authenticatedUserService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegisterRequestDto request) {
        UserResponseDto response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        User currentUser = authenticatedUserService.getCurrentUser();
        UserResponseDto response = UserResponseDto.from(currentUser);
        return ResponseEntity.ok(response);
    }
}
