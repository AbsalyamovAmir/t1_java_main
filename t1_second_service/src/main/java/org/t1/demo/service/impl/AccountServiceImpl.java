package org.t1.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.t1.demo.exception.AccountException;
import org.t1.demo.model.Account;
import org.t1.demo.repository.AccountRepository;
import org.t1.demo.service.AccountService;
import org.t1.demo.util.AccountMapper;

/**
 * Сервис для работы с сущностью Account
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountException("Account with ID " + id + " not found"));
    }

    @Override
    public Account findByAccountId(Account account) {
        return accountRepository.findByAccountId(account.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found: " + account));
    }

}
