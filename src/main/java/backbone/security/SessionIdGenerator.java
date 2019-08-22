package backbone.security;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Component
public class SessionIdGenerator {

    private static final int POOL_SIZE = 20;
    private static final int SESSION_SIZE = 32;

    private SecureRandom secureRandom = new SecureRandom();
    private List<String> sessionPool = new ArrayList<>(POOL_SIZE);

    @PostConstruct
    public void init() {

        for(int i=0; i < POOL_SIZE; i++) {
            sessionPool.add(newSessionId());
        }
        Collections.shuffle(sessionPool);
    }

    public String getSessionId() {

        String nextSessionId = sessionPool.remove(0);
        sessionPool.add(newSessionId());
        Collections.shuffle(sessionPool);

        return nextSessionId;
    }

    private String newSessionId() {
        byte[] bytes = new byte[SESSION_SIZE];
        secureRandom.nextBytes(bytes);
        return new String(Base64.getEncoder().encode(bytes));
    }
}
