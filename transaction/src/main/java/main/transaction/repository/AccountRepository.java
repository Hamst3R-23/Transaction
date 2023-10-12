package main.transaction.repository;

import main.transaction.model.Account;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("SELECT * FROM account WHERE name = :name")
    List<Account> findAccountsByName(String name);

    @Query("SELECT MAX(id) FROM account WHERE name = :name")
    Long findAccountByName(String name);

    @Modifying
    @Query("DELETE FROM account WHERE name = :name")
    void deleteAccountByName(String name);

    @Modifying
    @Query("UPDATE account SET amount = :amount WHERE id = :id")
    void changeAmount(long id, BigDecimal amount);

    @Query("SELECT EXISTS(SELECT * FROM account where id = :id)")
    Boolean checkAccount(long id);

    @Query("SELECT * FROM account where id = :id")
    List<Account> findAccountsById(long id);

}
