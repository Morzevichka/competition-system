package ru.morzevichka.competition_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.morzevichka.competition_system.dto.external.PageResponse;
import ru.morzevichka.competition_system.dto.pilot.CreatePilotRequest;
import ru.morzevichka.competition_system.dto.pilot.PilotResponse;
import ru.morzevichka.competition_system.dto.pilot.UpdatePilotRequest;
import ru.morzevichka.competition_system.exception.pilot.NicknameAlreadyTakenException;
import ru.morzevichka.competition_system.exception.pilot.PilotNotFoundException;
import ru.morzevichka.competition_system.exception.pilot.TgUsernameAlreadyTakenException;
import ru.morzevichka.competition_system.model.Pilot;
import ru.morzevichka.competition_system.repository.PilotRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PilotService {

    private final PilotRepository pilotRepository;

    public Page<Pilot> findPilots(Pageable pageable) {
        return pilotRepository.findAll(pageable);
    }

    public Pilot createPilot(CreatePilotRequest body) {

        if (pilotRepository.existsByNickname(body.nickname())) {
            throw new NicknameAlreadyTakenException("Nickname пилота занят");
        }

        if (pilotRepository.existsByTgUsername(body.tgUsername())) {
            throw new TgUsernameAlreadyTakenException("Telegram пилота занят");
        }

        Pilot pilot = Pilot.builder()
                .nickname(body.nickname())
                .firstName(body.firstName())
                .lastName(body.lastName())
                .tgUsername(body.tgUsername())
                .build();

        return pilotRepository.save(pilot);
    }

    public Pilot updatePilot(UUID id, UpdatePilotRequest body) {
        if (!pilotRepository.existsById(id)) {
            throw new PilotNotFoundException("Пилот с таким id не найден");
        }

        Pilot updatePilot = Pilot.builder()
                .id(body.id())
                .nickname(body.nickname())
                .firstName(body.firstName())
                .lastName(body.lastName())
                .tgUsername(body.tgUsername())
                .build();

        return pilotRepository.save(updatePilot);
    }

    public void deletePilot(UUID id) {
        if (!pilotRepository.existsById(id)) {
            throw new PilotNotFoundException("Пилот с таким id не найден");
        }

        pilotRepository.deleteById(id);
    }
}
