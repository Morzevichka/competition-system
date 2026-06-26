package ru.morzevichka.competition_system.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.morzevichka.competition_system.dto.external.PageResponse;
import ru.morzevichka.competition_system.dto.user.*;
import ru.morzevichka.competition_system.model.User;
import ru.morzevichka.competition_system.service.UserService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> me(@AuthenticationPrincipal UUID id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMeResponse.fromEntity(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserMeResponse> updateProfile(@AuthenticationPrincipal UUID id, @RequestBody UpdateMeRequest body) {
        User user = userService.updateProfile(id, body);
        return ResponseEntity.ok(UserMeResponse.fromEntity(user));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PageResponse<UserResponse>> getUsers(
            @PageableDefault(size = 20, page = 0) Pageable pageable
    ) {
        PageResponse<UserResponse> response = PageResponse.fromPage(
                userService.findAll(pageable),
                UserResponse::fromEntity
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest body) {
       User user = userService.createUser(
               body.email(),
               body.login(),
               body.firstName(),
               body.lastName(),
               body.password(),
               body.role()
       );

        return ResponseEntity
                .created(URI.create(String.format("/api/users/%s", user.getId())))
                .body(UserResponse.fromEntity(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest body
    ) {
        User user = userService.updateUser(id, body);
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deletePilot(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}