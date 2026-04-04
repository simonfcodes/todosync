package dev.simoncodes.todosync.user;

import dev.simoncodes.todosync.user.dto.UserRegisterRequestDto;
import dev.simoncodes.todosync.user.dto.UserResponseDto;
import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public UserResponseDto register(UserRegisterRequestDto request) {
        Optional<User> existingUser = userRepo.findByEmail(request.email());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot create user: user with " + request.email() + " already exists.");
        }
        String encodedPassword = encoder.encode(request.password());
        User newUser = new User();
        newUser.setEmail(request.email());
        newUser.setPasswordHash(encodedPassword);
        newUser = userRepo.save(newUser);
        return new UserResponseDto(newUser.getId(), newUser.getEmail());
    }
}