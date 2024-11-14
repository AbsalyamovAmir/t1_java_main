package org.t1.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.t1.demo.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Сущность Transaction
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * Сумма транзакции
     */
    @Column(name = "sum_transaction")
    private BigDecimal sumTransaction;

    /**
     * Время транзакции
     */
    @Column(name = "time_transaction")
    private Date timeTransaction;

    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @Column(name = "transaction_id")
    private long transactionId;

    @Column(name = "timestamp")
    private Date timestamp;
}