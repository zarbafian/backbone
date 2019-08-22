package backbone.security;

public enum AccountRole {

    USER("USER"),
    ADMIN("ADMIN");

    private String value;

    AccountRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
