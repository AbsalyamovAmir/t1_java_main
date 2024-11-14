package org.t1.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.t1.demo.model.Account;
import org.t1.demo.model.dto.AccountDto;
import org.t1.demo.service.AccountService;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaAccountConsumer {

    private final AccountService accountService;

    @KafkaListener(id = "${t1.kafka.consumer.groupAccount.group-id}",
            topics = "${t1.kafka.topic.demo_accounts}",
            containerFactory = "kafkaAccountListenerContainerFactory")
    public void accountConsumerListener(AccountDto accountDto,
                                        Acknowledgment ack) {
        log.debug("Account consumer: Обработка новых сообщений");

        try {
            Account savedAccount = accountService.saveAccount(accountDto);
            log.info("Account has been saved to DB: {}", savedAccount);
        } catch (Exception e) {
            log.error("Error occurred while saving account : {}", accountDto, e);
        } finally {
            ack.acknowledge();
        }
    }
}
