package ru.morzevichka.competition_system.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
public record PageResponse<T>(
        List<T> items,
        long total,
        int page,
        int size,
        @JsonProperty("total_pages")
        int totalPages
) {
    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }

    public static <E, D> PageResponse<D> fromPage(Page<E> page, Function<E, D> mapper) {
        return new PageResponse<>(
                page.get()
                        .map(mapper)
                        .toList(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }
}
