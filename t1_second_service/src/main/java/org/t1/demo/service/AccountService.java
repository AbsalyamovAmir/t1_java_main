package org.t1.demo.service;

import org.t1.demo.model.Account;
import org.t1.demo.model.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса для работы с сущностью Account
 */
public interface AccountService {

    Account findById(Long id);

    Account findByAccountId(Account account);
}
