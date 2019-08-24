package backbone.security;

import java.time.ZonedDateTime;

public class UserToken {

    private String username;
    private ZonedDateTime expiration;
    private String role;

    public UserToken(String username, ZonedDateTime expiration, String role) {
        this.username = username;
        this.expiration = expiration;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return "UserToken{" +
                "user='" + username + '\'' +
                ", expiration=" + expiration +
                ", role='" + role + '\'' +
                '}';
    }
}
