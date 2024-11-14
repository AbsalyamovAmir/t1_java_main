package org.t1.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.t1.demo.model.Account;
import org.t1.demo.model.Transaction;
import org.t1.demo.model.dto.TransactionDto;
import org.t1.demo.model.enums.AccountStatus;
import org.t1.demo.model.enums.TransactionStatus;
import org.t1.demo.service.AccountService;
import org.t1.demo.service.TransactionService;

import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTransactionConsumer {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final KafkaTransactionProducer kafkaTransactionProducer;

    @KafkaListener(id = "${t1.kafka.consumer.groupTransaction.group-id}",
            topics = "${t1.kafka.topic.demo_transactions}",
            containerFactory = "kafkaTransactionListenerContainerFactory")
    public void transactionConsumerListener(TransactionDto transactionDto,
                                            Acknowledgment ack) {
        log.debug("Transaction consumer: Обработка новых сообщений");


        try {
            Account account = transactionDto.getAccount();
            if (account.getAccountStatus() == AccountStatus.OPEN) {
                transactionDto.setTransactionStatus(TransactionStatus.REQUESTED);
                Transaction savedTransaction = transactionService.saveTransaction(transactionDto);
                Account updatedAccount = accountService.updateAccountSum(account, transactionDto.getSumTransaction());
                kafkaTransactionProducer.sendAccepted(
                        account.getClient().getId(),
                        account.getAccountId(),
                        savedTransaction.getTransactionId(),
                        Date.valueOf(LocalDate.now()),
                        transactionDto.getSumTransaction(),
                        account.getBalance()
                );
                log.info("Transaction has been saved to DB. Transaction: {}. Account: {}", savedTransaction, updatedAccount);
            } else {
                log.info("Transaction hasn't been saved to DB");
            }
        } catch (Exception e) {
            log.error("Error occurred while saving transaction : {}", transactionDto, e);
        } finally {
            ack.acknowledge();
        }
    }
}
