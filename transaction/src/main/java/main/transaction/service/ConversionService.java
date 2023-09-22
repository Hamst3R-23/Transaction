package main.transaction.service;

import main.transaction.enums.LogOperationEnum;
import main.transaction.exception.AccountNotFoundException;
import main.transaction.exception.ParserException;
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

    private final CBRService CBRService;


    public ConversionService(AccountRepository accountRepository, LogRepository logRepository, CBRService CBRService) {

        this.CBRService = CBRService;
        this.accountRepository = accountRepository;
        this.logRepository = logRepository;
    }

    public Account convertMoney(long id, String valute) {

        String logText;

        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Wrong ID!"));

        BigDecimal valuteAmount = ParserXml.parse(valute, new InputSource(new StringReader(CBRService.getDailyVolute()))).getValue();

        if (valuteAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new ParserException("Wrong name of valute!");
        }

        account.setAmount(account.getAmount().divide(valuteAmount, 2, RoundingMode.HALF_EVEN));

        logText = String.format("%s  with id: %d converted his amount to %s (%.2f)", account.getName(), id, valute, account.getAmount());

        logRepository.insertLogInfo(id, LogOperationEnum.CONVERT_MONEY.getOperation(), accountRepository.getAmountById(id), logText);

        return account;
    }

}
