package org.t1.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.t1.demo.exception.KafkaSendException;
import org.t1.demo.model.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class KafkaTransactionProducer {

    private static final String TOPIC_NAME_TRANSACTION = "t1_demo_transaction";
    private static final String TOPIC_NAME_TRANSACTION_ACCEPTED = "t1_demo_transaction_accepted";
    @Qualifier("transaction")
    private final KafkaTemplate<String, Object> transactionKafkaTemplate;

    public KafkaTransactionProducer(@Qualifier("transaction") KafkaTemplate<String, Object> transactionKafkaTemplate, KafkaTemplate kafkaTemplate) {
        this.transactionKafkaTemplate = transactionKafkaTemplate;
    }

    public TransactionDto send(TransactionDto transactionDto) {
        try {
            transactionKafkaTemplate.send(TOPIC_NAME_TRANSACTION, transactionDto);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new KafkaSendException("KafkaTransactionProducer sending error", ex);
        } finally {
            transactionKafkaTemplate.flush();
        }
        return transactionDto;
    }

    public void sendAccepted(long cliendId, long accountId, long transactionId,
                                       Date timestamp, BigDecimal amount, BigDecimal balance) {
        Map<String, Object> data = new HashMap<>();
        data.put("cliendId", cliendId);
        data.put("accountId", accountId);
        data.put("transactionId", transactionId);
        data.put("timestamp", timestamp);
        data.put("amount", amount);
        data.put("balance", balance);

        try {
            transactionKafkaTemplate.send(TOPIC_NAME_TRANSACTION_ACCEPTED, data);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new KafkaSendException("KafkaTransactionProducer sending error", ex);
        } finally {
            transactionKafkaTemplate.flush();
        }
    }
}
