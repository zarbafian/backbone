package backbone.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionIdGeneratorTest {

    @Autowired
    private SessionIdGenerator sessionIdGenerator;

    @Test
    public  void test() {

        String sessionId = sessionIdGenerator.getSessionId();
        Assert.assertNotNull(sessionId);
    }
}
