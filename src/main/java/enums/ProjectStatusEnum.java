package main.java.enums;

public enum ProjectStatusEnum {
    PLANNED("planned"),
    ON_HOLD("on_hold"),
    CANCELED("canceled"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed");

    private final String status;

    ProjectStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
