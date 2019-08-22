package backbone.dao;

import backbone.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDaoBean implements AccountDao {

    public Account findByUsername(String username) {

        /*
        if("toto".equals(username)) {
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
        else if("titi".equals(username)) {
            AccountEntity entity = new AccountEntity();
            entity.setId(2L);
            entity.setActive(true);
            entity.setDeleted(false);
            entity.setCreationDate(LocalDateTime.now());
            entity.setModificationDate(LocalDateTime.now());
            entity.setUsername(username);
            entity.setRole(AccountRole.USER);

            return entity;
        }*/

        return null;
    }
}
