package main.transaction.service;


import main.transaction.exception.AccountNotFoundException;
import main.transaction.httprequest.HttpRequest;
import main.transaction.model.Account;
import main.transaction.parser.MyParser;
import main.transaction.repository.AccountRepository;
import main.transaction.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ConversionService {

    private final AccountRepository accountRepository;

    private final LogRepository logRepository;

    public ConversionService(AccountRepository accountRepository, LogRepository logRepository) {

        this.accountRepository = accountRepository;
        this.logRepository = logRepository;
    }

    public Account convertMoney(long id, String valute) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException());

        BigDecimal valuteAmount = MyParser.parse(valute, new InputSource(new StringReader(HttpRequest.getRequest()))).getValue();

        account.setAmount(account.getAmount().divide(valuteAmount, 2, RoundingMode.HALF_UP));

        logRepository.insertLogInfo(id, "convertMoney", accountRepository.findById(id).get().getAmount(), account.getName() + " with id: " + id + " converted his amount to " + valute + " (" + account.getAmount() + ")");

        return account;

    }

}
