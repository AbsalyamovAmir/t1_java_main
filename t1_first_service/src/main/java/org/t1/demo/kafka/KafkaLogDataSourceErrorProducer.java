package org.t1.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.t1.demo.exception.KafkaSendException;
import org.t1.demo.model.dto.DataSourceErrorLogDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaLogDataSourceErrorProducer {

    private static final String TOPIC_NAME = "t1_demo_metrics";
    private static final String ERROR_TYPE = "DATA_SOURCE";
    private final KafkaTemplate<String, Object> logDataSourceErrorKafkaTemplate;

    /**
     * Метод для отправки сообщения в Kafka
     * @param dataSourceErrorLogDto Объект ошибки
     */
    public void send(DataSourceErrorLogDto dataSourceErrorLogDto) {
        Message<DataSourceErrorLogDto> message = MessageBuilder.withPayload(dataSourceErrorLogDto)
                .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                .setHeader("errorType", ERROR_TYPE)
                .build();
        try {
            logDataSourceErrorKafkaTemplate.send(message);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new KafkaSendException("KafkaLogDataSourceErrorProducer sending error", ex);
        } finally {
            logDataSourceErrorKafkaTemplate.flush();
        }
    }
}
