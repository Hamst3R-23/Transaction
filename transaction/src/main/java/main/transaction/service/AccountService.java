package main.transaction.service;

import main.transaction.enums.LogOperationEnum;
import main.transaction.exception.AccountNotFoundException;
import main.transaction.model.Account;
import main.transaction.repository.AccountRepository;
import main.transaction.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final LogRepository logRepository;

    private String logText;


    public AccountService(AccountRepository accountRepository, LogRepository logRepository) {
        this.accountRepository = accountRepository;
        this.logRepository = logRepository;
    }

    @Transactional
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
        logRepository.insertLogInfo(account.getId(), LogOperationEnum.SET_ACCOUNT.getOperation(), amount, logText);
    }

    @Transactional
    public void deleteAccount(String name) {
        Account account = accountRepository.findAccountByName(name).orElseThrow(() -> new AccountNotFoundException("Wrong name!"));
        logText = String.format("Delete account: name: %s with id: %d", name, account.getId());
        logRepository.insertLogInfo(account.getId(), LogOperationEnum.DELETE_ACCOUNT.getOperation(), account.getAmount(), logText);
        accountRepository.deleteAccountByName(account.getName());
    }

    public List<Account> getAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    public List<Account> findAccountById(long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Wrong ID!"));
        return accountRepository.findAccountsById(account.getId());
    }

    public List<Account> findAccountsByName(String name) {
        List<Account> accounts = accountRepository.findAccountsByName(name);
        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("Wrong name!");
        } else return accounts;
    }

}
