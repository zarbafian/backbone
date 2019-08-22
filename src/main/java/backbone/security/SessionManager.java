package backbone.security;

import backbone.entity.AccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Component
public class SessionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);

    private static final long SESSION_DURATION_VALUE = 20;
    private static final ChronoUnit SESSION_DURATION_UNIT = ChronoUnit.MINUTES;

    @Autowired
    private SessionIdGenerator sessionIdGenerator;

    private Map<String, BearerToken> validSessions = new HashMap<>();

    public UserSession createSession(AccountEntity accountEntity) {

        // session ID
        String sessionId = sessionIdGenerator.getSessionId();

        // expiration
        ZonedDateTime expiration = ZonedDateTime.now().plus(SESSION_DURATION_VALUE, SESSION_DURATION_UNIT);

        // bearer token
        BearerToken bearerToken = new BearerToken(accountEntity.getUsername(), expiration, accountEntity.getRole().getValue());

        validSessions.put(sessionId, bearerToken);

        LOGGER.debug("Created new session, sessionId={}, token={}", sessionId, bearerToken);

        return new UserSession(
                sessionId,
                bearerToken
        );
    }

    public boolean isValid(String sessionId) {

        if(!validSessions.containsKey(sessionId)) {
            LOGGER.trace("Session does not exist, sessionId={}", sessionId);
            return false;
        }

        BearerToken retrievedToken = validSessions.get(sessionId);

        if(ZonedDateTime.now().isAfter(retrievedToken.getExpiration())) {
            LOGGER.trace("Session is expired, sessionId={}, token={}", sessionId, retrievedToken.getExpiration());
            // token is expired
            validSessions.remove(sessionId);
            return false;
        }

        return true;
    }

    public void invalidateSession(String sessionId) {

        if(!validSessions.containsKey(sessionId)) {
            LOGGER.debug("Could not invalidate session as it did not exist, sessionId={}");
        }
        else {
            BearerToken removedSession = validSessions.remove(sessionId);
            LOGGER.debug("Session invalidated, sessionId={}, token={}", sessionId, removedSession);
        }
    }
}
