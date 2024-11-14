package org.t1.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.t1.demo.model.enums.AccountStatus;
import org.t1.demo.model.enums.AccountType;

import java.math.BigDecimal;

/**
 * Сущность Account
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Тип аккаунта
     */
    @Column(name = "account_type")
    private AccountType accountType;

    /**
     * Баланс
     */
    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "account_status")
    private AccountStatus accountStatus;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "frozen_amount")
    private BigDecimal frozenAmount;
}
