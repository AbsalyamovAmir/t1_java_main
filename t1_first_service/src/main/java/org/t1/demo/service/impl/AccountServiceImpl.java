package org.t1.demo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.t1.demo.exception.AccountException;
import org.t1.demo.exception.ClientException;
import org.t1.demo.generator.DataGenerator;
import org.t1.demo.kafka.KafkaAccountProducer;
import org.t1.demo.model.Account;
import org.t1.demo.model.Client;
import org.t1.demo.model.dto.AccountDto;
import org.t1.demo.repository.AccountRepository;
import org.t1.demo.service.AccountService;
import org.t1.demo.service.ClientService;
import org.t1.demo.util.AccountMapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для работы с сущностью Account
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientService clientService;
    private final AccountMapper accountMapper;
    private final KafkaAccountProducer kafkaAccountProducer;

    /**
     * Получение Account по id
     * @param id идентификатор записи Account
     * @return Dto для Account
     */
    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account with ID " + id + " not found"));
        return accountMapper.toDto(account);
    }

    /**
     * Сохранение Account
     * @param accountDto полученная Dto для Account
     */
    @Override
    @Transactional
    public Account saveAccount(AccountDto accountDto) {
        return accountRepository.save(accountMapper.toEntity(accountDto));
    }

    /**
     * Генерация записей Account
     * @param count количество записей
     */
    @Override
    @Transactional
    public void generateAccounts(int count) {
        List<Client> clients = clientService.findAll();
        if (clients.isEmpty()) {
            throw new ClientException("No clients found");
        }
        List<Account> accounts = DataGenerator.generateAccounts(count, clients);
        accountRepository.saveAll(accounts);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ClientException("Account with ID " + id + " not found"));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public Account sendAccount(AccountDto accountDto) {
        return accountMapper.toEntity(kafkaAccountProducer.send(accountDto));
    }

    @Override
    public Account updateAccountSum(Account account, BigDecimal sum) {
        BigDecimal updateBalance = account.getBalance().add(sum);
        account.setBalance(updateBalance);
        return accountRepository.save(account);
    }
}
