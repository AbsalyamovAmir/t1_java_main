package org.t1.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.t1.demo.exception.TransactionException;
import org.t1.demo.model.Transaction;
import org.t1.demo.model.dto.TransactionDto;
import org.t1.demo.model.enums.AccountStatus;
import org.t1.demo.model.enums.ClientStatus;
import org.t1.demo.model.enums.TransactionStatus;
import org.t1.demo.repository.ClientRepository;
import org.t1.demo.repository.TransactionRepository;
import org.t1.demo.service.RequestProcessingService;
import org.t1.demo.util.TransactionMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestProcessingServiceImpl implements RequestProcessingService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final TransactionMapper transactionMapper;

    @Value("${t1.requests.transaction-count}")
    private int maxCount;

    @Override
    public TransactionDto processRequest(TransactionDto transactionDto, ClientStatus clientStatus) {
        long transactionId = transactionMapper.toEntity(transactionDto).getId();
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionException("Transaction with ID " + transactionId + " not found"));
        if (clientStatus == ClientStatus.BLOCKED) {
            transaction.setTransactionStatus(TransactionStatus.REJECTED);
            transaction.getAccount().setAccountStatus(AccountStatus.BLOCKED);
            transaction.getAccount().getClient().setClientStatus(ClientStatus.BLOCKED);
        }
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }
}
