package org.t1.demo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.t1.demo.model.Account;
import org.t1.demo.model.dto.AccountDto;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    public Account toEntity(AccountDto accountDto) {
        return Account.builder()
                .client(accountDto.getClient())
                .accountType(accountDto.getAccountType())
                .balance(accountDto.getBalance())
                .accountStatus(accountDto.getAccountStatus())
                .accountId(accountDto.getAccountId())
                .frozenAmount(accountDto.getFrozenAmount())
                .build();
    }

    public AccountDto toDto(Account entity) {
        return AccountDto.builder()
                .id(entity.getId())
                .accountType(entity.getAccountType())
                .balance(entity.getBalance())
                .accountStatus(entity.getAccountStatus())
                .accountId(entity.getAccountId())
                .frozenAmount(entity.getFrozenAmount())
                .build();
    }
}
