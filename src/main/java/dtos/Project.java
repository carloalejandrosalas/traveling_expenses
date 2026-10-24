package main.java.dtos;

import main.java.enums.ProjectStatusEnum;

public record Project (
        Long id,
        String name,
        String description,
        String startDate,
        String endDate,
        Double budget,
        ProjectStatusEnum status,
        String createdAt,
        String updatedAt,
        Long ownerId
) {}
