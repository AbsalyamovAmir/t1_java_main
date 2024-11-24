package org.t1.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.t1.demo.kafka.KafkaTransactionProducer;
import org.t1.demo.model.Account;
import org.t1.demo.model.Transaction;
import org.t1.demo.repository.AccountRepository;
import org.t1.demo.repository.TransactionRepository;
import org.t1.demo.service.TransactionService;
import org.t1.demo.util.TransactionMapper;

import java.util.List;

/**
 * Сервис для работы с сущностью Transaction
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.findByAccount(account);
    }
}
