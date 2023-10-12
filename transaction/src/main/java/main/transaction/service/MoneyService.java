package main.transaction.service;

import main.transaction.enums.LogOperationEnum;
import main.transaction.exception.AccountNotFoundException;
import main.transaction.exception.NotEnoughMoneyException;
import main.transaction.model.Account;
import main.transaction.repository.AccountRepository;
import main.transaction.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MoneyService {

    private final LogRepository logRepository;

    private final AccountRepository accountRepository;

    private String logText;

    public MoneyService(AccountRepository accountRepository, LogRepository logRepository) {
        this.accountRepository = accountRepository;
        this.logRepository = logRepository;
    }

    public Account addMoney(long id, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NotEnoughMoneyException("Negative amount request!");
        }

        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Wrong ID!"));
        BigDecimal newAccountAmount = account.getAmount().add(amount);
        accountRepository.changeAmount(id, newAccountAmount);

        logText = String.format("Added money (%.2f) to %s  with id: %d", amount, account.getName(), id);
        logRepository.insertLogInfo(id, LogOperationEnum.ADD_MONEY.getOperation(), amount, logText);

        return account;
    }

    public Account subtractMoney(long id, BigDecimal amount) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Wrong ID!"));
        BigDecimal newAccountAmount = account.getAmount().subtract(amount);
        if (newAccountAmount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyException("Not enough money or negative amount!");
        }
        accountRepository.changeAmount(id, newAccountAmount);
        logText = String.format("Subtracted money (%.2f) from %s  with id: %d", amount, account.getName(), id);
        logRepository.insertLogInfo(id, LogOperationEnum.SUBTRACT_MONEY.getOperation(), amount, logText);

        return account;

    }

    public List<Account> transferMoney(
            long idSender,
            long idReceiver,
            BigDecimal amount
    ) {
        Account sender = accountRepository.findById(idSender).orElseThrow(() -> new AccountNotFoundException("Wrong sender ID!"));

        Account receiver = accountRepository.findById(idReceiver).orElseThrow(() -> new AccountNotFoundException("Wrong receiver ID!"));

        BigDecimal senderNewAmount = sender.getAmount().subtract(amount);

        BigDecimal receiverNewAmount = receiver.getAmount().add(amount);

        if (amount.compareTo(BigDecimal.ZERO) <= 0 || senderNewAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NotEnoughMoneyException("Not enough money or negative amount request!");
        }

        accountRepository.changeAmount(idSender, senderNewAmount);

        accountRepository.changeAmount(idReceiver, receiverNewAmount);

        logText = String.format("%s with id %d transferred money (%.2f) to %s with id %d ", sender.getName(), idSender, amount, receiver.getName(), idReceiver);

        logRepository.insertLogInfo(idSender, LogOperationEnum.TRANSFER_MONEY.getOperation(), amount, logText);

        logText = String.format("%s with id %d got money (%.2f) from %s with id %d ", receiver.getName(), idReceiver, amount, sender.getName(), idSender);

        logRepository.insertLogInfo(idReceiver, LogOperationEnum.TRANSFER_MONEY.getOperation(), amount, logText);

        List<Account> requestList = new ArrayList<>();

        requestList.add(accountRepository.findAccountById(idSender));

        requestList.add(accountRepository.findAccountById(idReceiver));

        return requestList;
    }

}
