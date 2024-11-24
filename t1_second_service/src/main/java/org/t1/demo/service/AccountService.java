package org.t1.demo.service;

import org.t1.demo.model.Account;

/**
 * Интерфейс сервиса для работы с сущностью Account
 */
public interface AccountService {

    Account findById(Long id);

    Account findByAccountId(Account account);
}
