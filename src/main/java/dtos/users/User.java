package main.java.dtos.users;

import main.java.enums.UsertTypeEnum;

import java.time.LocalDateTime;

public record User (
        Long id,
        String identificationNumber,
        String name,
        String surname,
        String phoneNumber,
        String address,
        String passwordHash,
        UsertTypeEnum type,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastLoginAt
) {
    public String fullName() {
        return "%s %s".formatted(this.name, this.surname);
    }
}
