package main.transaction.repository;

import main.transaction.model.Account;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Query("SELECT * FROM account WHERE name = :name")
    List<Account> findAccountsByName(String name);

    @Query("SELECT * FROM account WHERE name = :name")
    Optional<Account> findAccountByName(String name);

    @Modifying
    @Query("DELETE FROM account WHERE name = :name")
    void deleteAccountByName(String name);

    @Modifying
    @Query("UPDATE account SET amount = :amount WHERE id = :id")
    void changeAmount(long id, BigDecimal amount);

    @Query("SELECT EXISTS(SELECT * FROM account where name = :name)")
    Boolean checkAccountByName(String name);

    @Query("SELECT EXISTS(SELECT * FROM account where id = :id)")
    Boolean checkAccountById(long id);

    @Query("SELECT * FROM account where id = :id")
    List<Account> findAccountsById(long id);

    @Query("SELECT * FROM account where id = :id")
    Account findAccountById(long id);

    @Query("SELECT amount FROM account where id = :id")
    BigDecimal getAmountById(long id);

}
