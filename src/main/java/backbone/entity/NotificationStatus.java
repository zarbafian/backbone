package backbone.entity;

public enum  NotificationStatus {

    NEW("NEW"),
    IN_PROGRESS("IN_PROGRESS"),
    CLOSED("CLOSED");

    private String value;

    NotificationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
