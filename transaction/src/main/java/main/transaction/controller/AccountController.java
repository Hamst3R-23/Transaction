package main.transaction.controller;

import main.transaction.dto.TransferRequest;
import main.transaction.model.*;
import main.transaction.service.AccountService;
import main.transaction.service.ConversionService;
import main.transaction.service.LogService;
import main.transaction.service.MoneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/money/transfer")
    public ResponseEntity<List<Account>> transferMoney(@RequestBody TransferRequest body) {
        List<Account> requestlist = moneyService.transferMoney(body.getSenderAccountId(), body.getReceiverAccountId(), body.getAmount());
        return new ResponseEntity<>(requestlist, HttpStatus.OK);

    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(defaultValue = "") String name, @RequestParam(required = false) Long id) {
        if (name.isEmpty()) {
            if (id != null) {
                return new ResponseEntity<>(accountService.findAccountById(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(accountService.findAccountsByName(name), HttpStatus.OK);
        }

    }


    @PostMapping("/account/creation")
    public ResponseEntity<JsonResponseToCreateDeleteOperationsModel> setAccount(@RequestBody Account account) {
        accountService.setAccount(account.getName());
        return new ResponseEntity<>(new JsonResponseToCreateDeleteOperationsModel("Account created"), HttpStatus.OK);
    }

    @DeleteMapping("/account/deletion")
    public ResponseEntity<JsonResponseToCreateDeleteOperationsModel> deleteAccount(@RequestBody Account account) {
        accountService.deleteAccount(account.getName());
        return new ResponseEntity<>(new JsonResponseToCreateDeleteOperationsModel("Deleted!"), HttpStatus.OK);

    }

    @PostMapping("/money/addition")
    public ResponseEntity<Account> addMoney(@RequestBody Account account) {

        account = moneyService.addMoney(account.getId(), account.getAmount());
        return new ResponseEntity<>(account, HttpStatus.ACCEPTED);

    }

    @PostMapping("/money/subtract")
    public ResponseEntity<Account> subtractMoney(@RequestBody Account account) {
        account = moneyService.subtractMoney(account.getId(), account.getAmount());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/money/conversion")
    public ResponseEntity<Account> convert(@RequestParam long id, @RequestParam String valute) {
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.convertMoney(id, valute));
    }

    @GetMapping("/log")
    public ResponseEntity<JsonResponseToGetLogOperationModel> logging(@RequestBody SortingPaginationSettings sortingPaginationSettings) throws ClassNotFoundException {
        List<Log> logtList = logService.getAllLog(sortingPaginationSettings.getPageNum(), sortingPaginationSettings.getPageSize(), sortingPaginationSettings.getSortBy(), sortingPaginationSettings.getOrderDirection(), sortingPaginationSettings.getAccountid());
        return new ResponseEntity<>(new JsonResponseToGetLogOperationModel(logtList), HttpStatus.OK);
    }

}
