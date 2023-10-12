package main.transaction.validator;

import main.transaction.exception.AccountNotFoundException;
import main.transaction.exception.PagingAndSortingException;
import main.transaction.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Component
public class PagingAndSortingValidator {

    private static final Set<String> columns = new HashSet<>();

    private final AccountRepository accountRepository;


    public PagingAndSortingValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private Set<String> getFieldsName() throws ClassNotFoundException {
        Class logClass = Class.forName("main.transaction.model.Log");
        Field[] classFields = logClass.getDeclaredFields();
        for (Field field : classFields) {
            columns.add(field.getName());
        }
        return columns;
    }

    public void checkPagingAndSortingParams(String sortBy, String orderQue, long accountid) throws ClassNotFoundException {
        if (!getFieldsName().contains(sortBy)) {
            throw new PagingAndSortingException("Invalid sorting column!");

        } else if (!orderQue.equals("asc") && !orderQue.equals("desc")) {
            throw new PagingAndSortingException("Invalid order!");
        } else if (!accountRepository.checkAccountById(accountid)) {
            throw new AccountNotFoundException("Wrong ID!");
        }
    }

}
