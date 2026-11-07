package main.java.dtos.projects;

import main.java.enums.ProjectStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateProjectRequest(
    Long ownerId,
    String name,
    String description,
    BigDecimal budget,
    LocalDate startDate,
    LocalDate endDate,
    ProjectStatusEnum status
) {
}
