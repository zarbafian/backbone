package backbone.security;

import backbone.entity.AccountEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthenticationManager {

    public AccountEntity authenticate(String username, String password) {

        if("toto".equals(username) && "toto".equals(password)) {

            AccountEntity entity = new AccountEntity();
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

            AccountEntity entity = new AccountEntity();
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
