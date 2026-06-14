package ru.morzevichka.competition_system.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morzevichka.competition_system.dto.user.UserInfoResponse;
import ru.morzevichka.competition_system.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> me(@CookieValue("accessToken") String token) {
        UserInfoResponse userInfoResponse = userService.me(token);
        return ResponseEntity.ok(userInfoResponse);
    }
}
