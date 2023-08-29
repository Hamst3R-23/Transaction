package main.transaction.service;


import main.transaction.exception.AccountNotFoundException;
import main.transaction.httprequest.HttpRequest;
import main.transaction.model.Account;
import main.transaction.parser.ParserXml;
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

    private final HttpRequest httpRequest;

    private String logText;

    public ConversionService(AccountRepository accountRepository, LogRepository logRepository, HttpRequest httpRequest) {


        this.httpRequest = httpRequest;
        this.accountRepository = accountRepository;
        this.logRepository = logRepository;
    }


    public Account convertMoney(long id, String valute) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Wrong ID!"));

        BigDecimal valuteAmount = ParserXml.parse(valute, new InputSource(new StringReader(httpRequest.getRequest()))).getValue();

        account.setAmount(account.getAmount().divide(valuteAmount, 2, RoundingMode.HALF_UP));

        logText = String.format("%s  with id: %d converted his amount to %s (%.2f)", account.getName(), id, valute, account.getAmount());

        logRepository.insertLogInfo(id, "convertMoney", accountRepository.getAmountById(id), logText);

        return account;

    }

}
