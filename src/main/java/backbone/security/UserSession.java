package backbone.security;

public class UserSession {

    private String sessionId;
    private BearerToken bearerToken;

    public UserSession(String sessionId, BearerToken bearerToken) {
        this.sessionId = sessionId;
        this.bearerToken = bearerToken;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public BearerToken getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(BearerToken bearerToken) {
        this.bearerToken = bearerToken;
    }
}
