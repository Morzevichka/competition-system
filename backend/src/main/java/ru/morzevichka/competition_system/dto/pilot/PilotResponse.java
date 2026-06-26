package ru.morzevichka.competition_system.dto.pilot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.morzevichka.competition_system.model.Pilot;

import java.util.UUID;

@Builder
public record PilotResponse(
        UUID id,
        String nickname,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("tg_username")
        String tgUsername
) {
        public static PilotResponse fromEntity(Pilot pilot) {
                return PilotResponse.builder()
                        .id(pilot.getId())
                        .nickname(pilot.getNickname())
                        .firstName(pilot.getFirstName())
                        .lastName(pilot.getLastName())
                        .tgUsername(pilot.getTgUsername())
                        .build();
        }
}
