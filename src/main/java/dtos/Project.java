package main.java.dtos;

import main.java.enums.ProjectStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record Project (
        Long id,
        Long ownerId,
        String ownerName,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal budget,
        ProjectStatusEnum status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
