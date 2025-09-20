package org.example.demo_library.domain.dto;

import java.time.LocalDateTime;

public record BorrowingRecordDto(
        Long id,
        Long inventoryId,
        String isbn,
        String bookName,
        LocalDateTime borrowingTime,
        LocalDateTime returnTime
) {}