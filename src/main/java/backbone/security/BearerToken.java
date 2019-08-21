package backbone.security;

public class BearerToken {

    private String user;
    private long expiration;
    private String role;

    public BearerToken(String user, long expiration, String role) {
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

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
