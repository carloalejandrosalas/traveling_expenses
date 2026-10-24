package main.java.dtos.users;

import main.java.enums.UsertTypeEnum;

public record AuthUser(
        Long id,
        String identificationNumber,
        String name,
        String surname,
        String phoneNumber,
        String address,
        UsertTypeEnum type
) {
}
