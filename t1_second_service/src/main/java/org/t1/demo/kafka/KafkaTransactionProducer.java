package org.t1.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.t1.demo.model.dto.TransactionDto;

@Component
@Slf4j
public class KafkaTransactionProducer {

    private static final String RESULT_TOPIC = "t1_demo_transaction_result";
    @Qualifier("transaction")
    private final KafkaTemplate<String, Object> transactionKafkaTemplate;

    public KafkaTransactionProducer(KafkaTemplate<String, Object> transactionKafkaTemplate) {
        this.transactionKafkaTemplate = transactionKafkaTemplate;
    }

    public void sendTransactionResult(TransactionDto transactionDto) {
        transactionKafkaTemplate.send(RESULT_TOPIC, transactionDto);
        log.info("Transaction result sent to {}: {}", RESULT_TOPIC, transactionDto);
    }

}
