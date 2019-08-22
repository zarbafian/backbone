package backbone.dao;

import backbone.entity.Account;

public interface AccountDao {

    Account findByUsername(String username);
}
