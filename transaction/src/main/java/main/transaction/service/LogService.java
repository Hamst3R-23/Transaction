package main.transaction.service;

import main.transaction.model.Account;
import main.transaction.model.Log;
import main.transaction.repository.LogRepository;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    public List<Log> getAllAccounts(Integer pageNo, Integer pageSize, String sortBy, String orderQue, long accountid) {

        Order order = new Order(Sort.Direction.fromString(orderQue), sortBy);

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(order));
        Page<Log> pageResult = logRepository.findAllByAccountid(paging, accountid);

        if (pageResult.hasContent()) {
            return pageResult.getContent();
        } else {
            return new ArrayList<Log>();
        }
    }

}
