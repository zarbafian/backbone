package backbone.security;

import backbone.entity.Account;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthenticationManager {

    public Account authenticate(String username, String password) {

        if("toto".equals(username) && "toto".equals(password)) {

            Account entity = new Account();
            entity.setId(1L);
            entity.setActive(true);
            entity.setDeleted(false);
            entity.setCreationDate(LocalDateTime.now());
            entity.setModificationDate(LocalDateTime.now());
            entity.setUsername(username);
            entity.setRole(AccountRole.ADMIN);

            return entity;
        }
        else if("titi".equals(username) && "titi".equals(password)) {

            Account entity = new Account();
            entity.setId(2L);
            entity.setActive(true);
            entity.setDeleted(false);
            entity.setCreationDate(LocalDateTime.now());
            entity.setModificationDate(LocalDateTime.now());
            entity.setUsername(username);
            entity.setRole(AccountRole.USER);

            return entity;
        }
        else {
            return null;
        }
    }
}
