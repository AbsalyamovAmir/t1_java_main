package org.t1.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.t1.demo.exception.KafkaSendException;
import org.t1.demo.model.dto.MetricDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMetricProducer {

    private static final String TOPIC_NAME = "t1_demo_metrics";
    private static final String ERROR_TYPE = "METRICS";
    private final KafkaTemplate<String, Object> metricsKafkaTemplate;

    /**
     * Метод для отправки сообщения в Kafka
     * @param metricDto Объект ошибки
     */
    public void send(MetricDto metricDto) {
        Message<MetricDto> message = MessageBuilder.withPayload(metricDto)
                .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                .setHeader("errorType", ERROR_TYPE)
                .build();
        try {
            metricsKafkaTemplate.send(message);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new KafkaSendException("KafkaMetricProducer sending error", ex);
        } finally {
            metricsKafkaTemplate.flush();
        }
    }
}

