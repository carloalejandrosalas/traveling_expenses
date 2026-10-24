package main.java.dtos.auth;

public record LoginRequest(
    String identificationNumber,
    String password
) {
}
