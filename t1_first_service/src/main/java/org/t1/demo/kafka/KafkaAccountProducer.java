package org.t1.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.t1.demo.exception.KafkaSendException;
import org.t1.demo.model.dto.AccountDto;

@Component
@Slf4j
public class KafkaAccountProducer {

    private static final String TOPIC_NAME = "t1_demo_accounts";
    @Qualifier("account")
    private final KafkaTemplate<String, Object> accountKafkaTemplate;

    public KafkaAccountProducer(@Qualifier("account") KafkaTemplate<String, Object> accountKafkaTemplate) {
        this.accountKafkaTemplate = accountKafkaTemplate;
    }

    /**
     * Метод для отправки сообщения в Kafka
     * @param accountDto Объект ошибки
     */
    public AccountDto send(AccountDto accountDto) {
        try {
            accountKafkaTemplate.send(TOPIC_NAME, accountDto);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new KafkaSendException("KafkaAccountProducer sending error", ex);
        } finally {
            accountKafkaTemplate.flush();
        }
        return accountDto;
    }
}
