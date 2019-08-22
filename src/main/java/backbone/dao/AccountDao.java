package backbone.dao;

import backbone.AccountEntity;

public interface AccountDao {
    AccountEntity findByUsername();
}
