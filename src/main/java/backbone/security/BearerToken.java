package backbone.security;

import java.time.ZonedDateTime;

public class BearerToken {

    private String user;
    private ZonedDateTime expiration;
    private String role;

    public BearerToken(String user, ZonedDateTime expiration, String role) {
        this.user = user;
        this.expiration = expiration;
        this.role = role;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ZonedDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(ZonedDateTime expiration) {
        this.expiration = expiration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "BearerToken{" +
                "user='" + user + '\'' +
                ", expiration=" + expiration +
                ", role='" + role + '\'' +
                '}';
    }
}
