package net.tuchnyak.dto;

import java.util.Collections;
import java.util.List;

/**
 * @author tuchnyak (George Shchennikov)
 */
public record Page<T>(
        List<T> items,
        int currentPage,
        int totalItems,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {

    public static <T> Page<T> empty() {

        return new Page<>(
                Collections.emptyList(),
                0,
                0,
                0,
                false,
                false
        );
    }

}
