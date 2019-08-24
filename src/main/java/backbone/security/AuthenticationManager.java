package backbone.security;

import backbone.entity.Account;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthenticationManager {

    public Account authenticate(String username, String password) {

        if("thomas".equals(username) && "thomas".equals(password)) {

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
        else if("maximilien".equals(username) && "maximilien".equals(password)) {

            Account entity = new Account();
            entity.setId(2L);
            entity.setActive(true);
            entity.setDeleted(false);
            entity.setCreationDate(LocalDateTime.now());
            entity.setModificationDate(LocalDateTime.now());
            entity.setUsername(username);
            entity.setRole(AccountRole.ADMIN);

            return entity;
        }
        else if("pouriya".equals(username) && "pouriya".equals(password)) {

            Account entity = new Account();
            entity.setId(3L);
            entity.setActive(true);
            entity.setDeleted(false);
            entity.setCreationDate(LocalDateTime.now());
            entity.setModificationDate(LocalDateTime.now());
            entity.setUsername(username);
            entity.setRole(AccountRole.ADMIN);

            return entity;
        }
        else {
            return null;
        }
    }
}
