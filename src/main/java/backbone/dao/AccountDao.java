package backbone.dao;

import backbone.entity.AccountEntity;

public interface AccountDao {

    AccountEntity findByUsername(String username);
}
