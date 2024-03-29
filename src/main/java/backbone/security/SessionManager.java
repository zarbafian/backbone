package backbone.security;

import backbone.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Component
public class SessionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);

    private static final long SESSION_DURATION_VALUE = 4;
    private static final ChronoUnit SESSION_DURATION_UNIT = ChronoUnit.HOURS;

    @Autowired
    private SessionIdGenerator sessionIdGenerator;

    private Map<String, UserToken> validSessions = new HashMap<>();

    @PostConstruct
    public void init() {

        String mySid = "lk1waRs4lHQbQyM3jPBfSaVEsJ9nsx+EtjRNnXQG+Rw=";

        //TODO: FOR TESTING ONLY
        validSessions.put(mySid, new UserToken("pouriya", mySid, ZonedDateTime.now().plus(7, ChronoUnit.DAYS), AccountRole.USER.getValue()));
    }

    public UserToken createSession(Account accountEntity) {

        // session ID
        String sessionId = sessionIdGenerator.getSessionId();

        // expiration
        ZonedDateTime expiration = ZonedDateTime.now().plus(SESSION_DURATION_VALUE, SESSION_DURATION_UNIT);

        // user token
        UserToken userToken = new UserToken(accountEntity.getUsername(), sessionId, expiration, accountEntity.getRole().getValue());

        validSessions.put(sessionId, userToken);

        LOGGER.debug("Created new session, userToken={}", userToken);

        return userToken;
    }

    public UserToken getSession(String sessionId) {

        if(!validSessions.containsKey(sessionId)) {
            LOGGER.trace("Session does not exist, sessionId={}", sessionId);
            return null;
        }

        UserToken retrievedToken = validSessions.get(sessionId);

        if(ZonedDateTime.now().isAfter(retrievedToken.getExpiration())) {
            LOGGER.trace("Session is expired, sessionId={}, token={}", sessionId, retrievedToken.getExpiration());
            // token is expired
            validSessions.remove(sessionId);
            return null;
        }

        return retrievedToken;
    }

    public void invalidateSession(String sessionId) {

        if(!validSessions.containsKey(sessionId)) {
            LOGGER.debug("Could not invalidate session as it did not exist, sessionId={}");
        }
        else {
            UserToken removedSession = validSessions.remove(sessionId);
            LOGGER.debug("Session invalidated, sessionId={}, token={}", sessionId, removedSession);
        }
    }
}
