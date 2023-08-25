package main.transaction.repository;

import main.transaction.model.Account;
import main.transaction.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface LogRepository extends PagingAndSortingRepository<Log, Long> {

    @Modifying
    @Query("INSERT INTO log (accountId, operation, amount, log, time) values (:accountId, :operation, :amount, :log, CURRENT_TIMESTAMP)")
    void insertLogInfo(@Param("accountId") long accountId, @Param("operation") String operation, @Param("amount") BigDecimal amount, @Param("log") String log);


    Page<Log> findAllByAccountid(Pageable pageable, long id);

}
