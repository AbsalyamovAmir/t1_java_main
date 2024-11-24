package org.t1.demo.service;

import org.t1.demo.model.Account;
import org.t1.demo.model.Transaction;

import java.util.List;

/**
 * Интерфейс сервиса для работы с сущностью Transaction
 */
public interface TransactionService {

    List<Transaction> getTransactionsByAccount(Account account);
}
