package org.t1.demo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.t1.demo.model.Transaction;
import org.t1.demo.model.dto.TransactionDto;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

    public Transaction toEntity(TransactionDto transactionDto) {
        return Transaction.builder()
                .account(transactionDto.getAccount())
                .sumTransaction(transactionDto.getSumTransaction())
                .timeTransaction(transactionDto.getTimeTransaction())
                .transactionStatus(transactionDto.getTransactionStatus())
                .transactionId(transactionDto.getTransactionId())
                .timestamp(transactionDto.getTimestamp())
                .build();
    }

    public TransactionDto toDto(Transaction entity) {
        return TransactionDto.builder()
                .id(entity.getId())
                .sumTransaction(entity.getSumTransaction())
                .timeTransaction(entity.getTimeTransaction())
                .transactionStatus(entity.getTransactionStatus())
                .transactionId(entity.getTransactionId())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
