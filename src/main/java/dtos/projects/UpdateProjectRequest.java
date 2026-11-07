package main.java.dtos.projects;

import main.java.enums.ProjectStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateProjectRequest(
    Long id,
    String name,
    String description,
    BigDecimal budget,
    LocalDate startDate,
    LocalDate endDate
) {
}
