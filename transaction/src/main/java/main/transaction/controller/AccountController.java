package main.transaction.controller;

import main.transaction.dto.TransferRequest;
import main.transaction.exception.AccountNotFoundException;
import main.transaction.exception.NotEnoughMoneyException;
import main.transaction.model.Account;
import main.transaction.model.Log;
import main.transaction.model.SortingPaginationSettings;
import main.transaction.service.AccountService;
import main.transaction.service.ConversionService;
import main.transaction.service.LogService;
import main.transaction.service.MoneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RestController
public class AccountController {

    private final AccountService accountService;

    private final MoneyService moneyService;

    private final ConversionService conversionService;

    private final LogService logService;

    public AccountController(AccountService accountService, MoneyService moneyService, ConversionService conversionService, LogService logService) {
        this.accountService = accountService;
        this.moneyService = moneyService;
        this.conversionService = conversionService;
        this.logService = logService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(
            @RequestBody TransferRequest body
    ) {

        try {
            List<Account> requestlist = moneyService.transferMoney(
                    body.getSenderAccountId(),
                    body.getReceiverAccountId(),
                    body.getAmount()
            );
            return new ResponseEntity<List<Account>>(requestlist, HttpStatus.OK);
        } catch (AccountNotFoundException | NotEnoughMoneyException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getAllAccounts(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) Long id
    ) {
        try {
            if (name.isEmpty()) {
                if (id != null) {
                    return new ResponseEntity<List<Account>>(accountService.findAccountById(id), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Iterable<Account>>(accountService.getAllAccounts(), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<List<Account>>(accountService.findAccountsByName(name), HttpStatus.OK);
            }
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/creation")
    public void setAccount(
            @RequestBody Account account
    ) {
        accountService.setAccount(account.getName());
    }

    @PostMapping("/deletion")
    public ResponseEntity<String> deleteAccount(
            @RequestBody Account account
    ) {
        try {
            accountService.deleteAccount(account.getName());
            return new ResponseEntity<>("Deleted!", HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addMoney(
            @RequestBody Account account
    ) {
        try {
            account = moneyService.addMoney(account.getId(), account.getAmount());
            return new ResponseEntity<Account>(account, HttpStatus.ACCEPTED);
        } catch (AccountNotFoundException | NotEnoughMoneyException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/subtract")
    public ResponseEntity<?> subtractMoney(
            @RequestBody Account account
    ) {
        try {

            account = moneyService.subtractMoney(account.getId(), account.getAmount());
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        } catch (AccountNotFoundException | NotEnoughMoneyException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/convert")
    public ResponseEntity<?> convert(@RequestParam long id,
                                           @RequestParam String valute
    ) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(conversionService.convertMoney(id, valute));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/log")
    public ResponseEntity<List<Log>> logging(
            /*@RequestParam(defaultValue = "0") Integer pageNum,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String orderQue,
            @RequestParam(required = true) Long accountid*/
            @RequestBody SortingPaginationSettings sortingPaginationSettings
    ) {
        if (!sortingPaginationSettings.getOrderQue().equals("asc") && !sortingPaginationSettings.getOrderQue().equals("desc")) {
            return new ResponseEntity<List<Log>>(HttpStatus.BAD_REQUEST);
        }

        List<Log> accountList = logService.getAllAccounts(sortingPaginationSettings.getPageNum(), sortingPaginationSettings.getPageSize(), sortingPaginationSettings.getSortBy(), sortingPaginationSettings.getOrderQue(), sortingPaginationSettings.getAccountid());

        return new ResponseEntity<List<Log>>(accountList, HttpStatus.OK);

    }

}
