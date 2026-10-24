package main.java.dtos;

public record UpsertProjectRequest(
    String name,
    String description,
    String budget,
    String startDate,
    String endDate,
    String status
) {
}
