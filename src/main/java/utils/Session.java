package main.java.utils;

import main.java.dtos.users.AuthUser;
import main.java.enums.UsertTypeEnum;

import java.time.LocalDateTime;

public class Session {
    private AuthUser authUser;
    private LocalDateTime loggedInAt;

    public Session(AuthUser authUser) {
        this.authUser = authUser;
        this.loggedInAt = LocalDateTime.now();
    }

    public boolean isAdmin() {
        return authUser.type() == UsertTypeEnum.ADMIN;
    }

    public boolean isEmployee() {
        return authUser.type() == UsertTypeEnum.EMPLOYEE;
    }

    public LocalDateTime getLoggedInAt() {
        return loggedInAt;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }
}
