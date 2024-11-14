package org.t1.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.t1.demo.model.Account;
import org.t1.demo.model.dto.TransactionDto;
import org.t1.demo.model.enums.TransactionStatus;
import org.t1.demo.service.AccountService;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTransactionConsumer {

    private final KafkaTransactionProducer transactionResultProducer;
    private final AccountService accountService;
    @Value("${t1.kafka.transaction.max-period}")
    private long maxPeriod;

    @Value("${t1.kafka.transaction.max-count}")
    private int maxCount;

    private final ConcurrentHashMap<Long, List<TransactionDto>> transactionTrackers = new ConcurrentHashMap<>();

    @KafkaListener(id = "${t1.kafka.consumer.group-id}",
            topics = "${t1.kafka.topic.transaction_result}",
            containerFactory = "customKafkaListenerContainerFactory")
    public void transactionAcceptMessageConsumer(Map<String, Object> confirmationData) {
        log.debug("Account consumer: Обработка новых сообщений");

        TransactionDto transactionDto = new TransactionDto();
        Account account = accountService.findByAccountId((Account) confirmationData.get("account"));
        transactionDto.setAccount(account);
        transactionDto.setTransactionId((Long) confirmationData.get("transactionId"));
        transactionDto.setTimestamp(Date.valueOf(String.valueOf(LocalDateTime.parse(confirmationData.get("timestamp").toString()))));
        transactionDto.setSumTransaction(new BigDecimal(confirmationData.get("amount").toString()));

        try {
            BigDecimal newBalance;
            if (transactionDto.getSumTransaction().compareTo(BigDecimal.ZERO) < 0) {  // Значит, пытаемся списать средства
                newBalance = account.getBalance().add(transactionDto.getSumTransaction());
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    log.warn("There are not enough funds in account {}. Transaction rejected.", transactionDto.getAccount());
                    sendResult(transactionDto, "REJECTED");
                    return;
                }
            }
            if (!isSuspiciousActivity(transactionDto))
                return;

            sendResult(transactionDto, "ACCEPTED");

        } catch (Exception e) {
            log.error("Error processing transaction: {}", e.getMessage());
        }
    }

    private boolean isSuspiciousActivity(TransactionDto transactionDto) {
        boolean result = true;

        Long accountId = transactionDto.getAccount().getId();

        List<TransactionDto> transactions  = transactionTrackers.computeIfAbsent(accountId,
                id -> new ArrayList<>());
        transactions.removeIf(transaction ->
                transaction.getTimestamp()
                        .before(Date.valueOf(String.valueOf(LocalDateTime.now()
                                .minusSeconds(maxPeriod)))));
        transactions.add(transactionDto);

        if (transactions.size() > maxCount) {
            for (TransactionDto transaction : transactions) {
                sendResult(transaction, "BLOCKED");
            }

            log.warn("Account {} transaction limit exceeded.", accountId);
            transactions.clear();
            result = false;
        }
        return result;
    }

    private void sendResult(TransactionDto transactionDto, String status) {
        transactionDto.setTransactionStatus(TransactionStatus.valueOf(status));
        transactionResultProducer.sendTransactionResult(transactionDto);
    }
}
