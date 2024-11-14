package org.t1.demo.service;

import org.t1.demo.model.Transaction;
import org.t1.demo.model.dto.TransactionDto;

/**
 * Интерфейс сервиса для работы с сущностью Transaction
 */
public interface TransactionService {

    TransactionDto getTransactionById(Long id);

    Transaction saveTransaction(TransactionDto transactionDto);

    void generateTransactions(int count);

    Transaction sendTransaction(TransactionDto transactionDto);
}
