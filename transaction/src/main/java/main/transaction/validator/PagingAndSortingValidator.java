package main.transaction.validator;

import main.transaction.exception.AccountNotFoundException;
import main.transaction.exception.PagingAndSortingException;
import main.transaction.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class PagingAndSortingValidator {


    private final static HashSet<String> columns = new HashSet<String>(Arrays.asList("id", "accountid", "operation", "amount", "time", "log"));

    private final AccountRepository accountRepository;

    public PagingAndSortingValidator(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public void checkPagingAndSortingParams(String sortBy, String orderQue,long accountid) {
        if (!columns.contains(sortBy)) {
            throw new PagingAndSortingException("Invalid sorting column!");

        } else if (!orderQue.equals("asc") && !orderQue.equals("desc")) {
            throw new PagingAndSortingException("Invalid order!");
        } else if (!accountRepository.checkAccountById(accountid)) {
            throw new AccountNotFoundException("Wrong ID!");
        }
    }
}
