package main.transaction.service;

import main.transaction.model.Account;
import main.transaction.repository.AccountRepository;
import main.transaction.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final LogRepository logRepository;


    public AccountService(AccountRepository accountRepository, LogRepository logRepository) {
        this.accountRepository = accountRepository;
        this.logRepository = logRepository;
    }

    public void setAccount(String name) {
        setAccount(name, BigDecimal.ZERO);
    }

    public void setAccount(String name, BigDecimal amount) {
        Account account = new Account();
        account.setName(name);
        account.setAmount(amount);
        accountRepository.save(account);
        logRepository.insertLogInfo(account.getId(), "setAccount", amount, "New account created: name:" + name + " id:" + account.getId());
    }

    public void deleteAccount(String name) {
        logRepository.insertLogInfo(accountRepository.findAccountByName(name), "deleteAccount", null, "Delete account: name:" + name + " id:" + accountRepository.findAccountByName(name));
        accountRepository.deleteAccountByName(name);
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> findAccountById(long id){
        return accountRepository.findAccountsById(id);
    }



    public List<Account> findAccountByName(String name) {
        return accountRepository.findAccountsByName(name);
    }

}
