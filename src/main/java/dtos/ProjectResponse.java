package main.java.dtos;

public record ProjectResponse(
        String name,
        String description,
        String startDate,
        String endDate,
        Double budget,
        String status,
        String createdAt,
        String updatedAt,
        Long ownerId
) {
}
