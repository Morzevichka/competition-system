package ru.morzevichka.competition_system.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morzevichka.competition_system.dto.meta.MetaResponse;
import ru.morzevichka.competition_system.model.UserRole;

@RestController
@RequestMapping("/api/meta")
@RequiredArgsConstructor
public class MetaController {

    @GetMapping("/roles")
    public ResponseEntity<MetaResponse<UserRole>> getRoles() {
        return ResponseEntity.ok(MetaResponse.of(UserRole.values()));
    }
}
