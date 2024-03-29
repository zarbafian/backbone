package backbone.security;

import java.time.ZonedDateTime;

public class UserToken {

    private String username;
    private String token;
    private ZonedDateTime expiration;
    private String role;

    @Override
    public String toString() {
        return "UserToken{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", expiration=" + expiration +
                ", role='" + role + '\'' +
                '}';
    }

    public UserToken() {
    }

    public UserToken(String username, String token, ZonedDateTime expiration, String role) {
        this.username = username;
        this.token = token;
        this.expiration = expiration;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
