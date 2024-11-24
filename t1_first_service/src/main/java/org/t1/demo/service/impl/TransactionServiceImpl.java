package org.t1.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.t1.demo.exception.AccountException;
import org.t1.demo.exception.TransactionException;
import org.t1.demo.generator.DataGenerator;
import org.t1.demo.kafka.KafkaTransactionProducer;
import org.t1.demo.model.Account;
import org.t1.demo.model.Transaction;
import org.t1.demo.model.dto.TransactionDto;
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
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;
    private final KafkaTransactionProducer kafkaTransactionProducer;

    /**
     * Получение Transaction по id
     * @param id идентификатор записи Transaction
     * @return Dto для Transaction
     */
    @Override
    public TransactionDto getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionException("Transaction with ID " + id + " not found"));
        return transactionMapper.toDto(transaction);
    }

    /**
     * Сохранение Transaction
     * @param transactionDto полученная Dto для Transaction
     */
    @Override
    @Transactional
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        return transactionMapper.toDto(transactionRepository.save(transactionMapper.toEntity(transactionDto)));
    }

    /**
     * Генерация записей Transaction
     * @param count количество записей
     */
    @Override
    public void generateTransactions(int count) {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            throw new AccountException("No accounts found");
        }
        List<Transaction> transactions = DataGenerator.generateTransactions(count, accounts);
        transactionRepository.saveAll(transactions);
    }

    @Override
    public Transaction sendTransaction(TransactionDto transactionDto) {
        return transactionMapper.toEntity(kafkaTransactionProducer.send(transactionDto));
    }
}
