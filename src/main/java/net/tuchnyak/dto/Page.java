package net.tuchnyak.dto;

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
}
