package ru.morzevichka.competition_system.dto.meta;

import java.util.List;

public record MetaResponse<T>(
        List<T> items
) {

    public static <T> MetaResponse<T> of(T[] values) {
        return new MetaResponse<T>(List.of(values));
    }
}
