package ru.morzevichka.competition_system.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.morzevichka.competition_system.dto.external.PageResponse;
import ru.morzevichka.competition_system.dto.pilot.CreatePilotRequest;
import ru.morzevichka.competition_system.dto.pilot.PilotResponse;
import ru.morzevichka.competition_system.dto.pilot.UpdatePilotRequest;
import ru.morzevichka.competition_system.model.Pilot;
import ru.morzevichka.competition_system.service.PilotService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pilots")
public class PilotController {

    private final PilotService pilotService;

    @GetMapping
    public ResponseEntity<PageResponse<PilotResponse>> getPilots(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        PageResponse<PilotResponse> response = PageResponse.fromPage(
                pilotService.findPilots(pageable),
                PilotResponse::fromEntity
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PilotResponse> createPilot(
            @Valid @RequestBody CreatePilotRequest body
    ) {
        Pilot pilot = pilotService.createPilot(body);
        return ResponseEntity
                .created(URI.create(String.format("/api/pilots/%s", pilot.getId())))
                .body(PilotResponse.fromEntity(pilot));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PilotResponse> updatePilot(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePilotRequest body
    ) {
        Pilot pilot = pilotService.updatePilot(id, body);
        return ResponseEntity.ok(PilotResponse.fromEntity(pilot));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deletePilot(@PathVariable UUID id) {
        pilotService.deletePilot(id);
        return ResponseEntity.noContent().build();
    }
}