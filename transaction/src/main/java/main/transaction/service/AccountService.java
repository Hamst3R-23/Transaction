package main.transaction.service;

import main.transaction.exception.AccountNotFoundException;
import main.transaction.model.Account;
import main.transaction.repository.AccountRepository;
import main.transaction.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final LogRepository logRepository;

    private String logText;


    public AccountService(AccountRepository accountRepository, LogRepository logRepository) {
        this.accountRepository = accountRepository;
        this.logRepository = logRepository;
    }

    public void setAccount(String name) {
        setAccount(name, BigDecimal.ZERO);
    }

    @Transactional
    public void setAccount(String name, BigDecimal amount) {
        Account account = new Account();
        account.setName(name);
        account.setAmount(amount);
        accountRepository.save(account);
        logText = String.format("New account created: name: %s with id: %d", name, account.getId());
        logRepository.insertLogInfo(account.getId(), "setAccount", amount, logText);
    }

    @Transactional
    public void deleteAccount(String name) {
        if (!accountRepository.checkAccountByName(name)) {
            throw new AccountNotFoundException("Wrong name!");
        }
        logText = String.format("Delete account: name: %s with id: %d", name, accountRepository.findAccountByName(name));
        logRepository.insertLogInfo(accountRepository.findAccountByName(name), "deleteAccount", BigDecimal.ZERO, logText);
        accountRepository.deleteAccountByName(name);
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> findAccountById(long id) {
        if (!accountRepository.checkAccountById(id)) {
            throw new AccountNotFoundException("Wrong ID!");
        }
        return accountRepository.findAccountsById(id);
    }


    public List<Account> findAccountsByName(String name) {
        if (!accountRepository.checkAccountByName(name)) {
            throw new AccountNotFoundException("Wrong name!");
        }
        return accountRepository.findAccountsByName(name);
    }

}
