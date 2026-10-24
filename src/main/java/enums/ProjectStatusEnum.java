package main.java.enums;

public enum ProjectStatusEnum {
    ACTIVE("active"),
    COMPLETED("completed"),
    ON_HOLD("on_hold");

    private final String status;

    ProjectStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
