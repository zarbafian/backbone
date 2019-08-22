package backbone.security;

public class UserSession {

    private String sessionId;
    private UserToken userToken;

    public UserSession(String sessionId, UserToken userToken) {
        this.sessionId = sessionId;
        this.userToken = userToken;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }
}
