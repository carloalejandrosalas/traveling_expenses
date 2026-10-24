package main.java.dtos.users;

import main.java.enums.UsertTypeEnum;

public record CreateNewUser(
        String identificationNumber,
        String name,
        String surname,
        String phoneNumber,
        String address,
        String password,
        UsertTypeEnum type
) {
}
