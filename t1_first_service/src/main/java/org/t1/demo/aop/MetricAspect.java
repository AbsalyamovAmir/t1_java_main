package org.t1.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.t1.demo.kafka.KafkaMetricProducer;
import org.t1.demo.model.dto.MetricDto;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class MetricAspect {

    private final KafkaMetricProducer kafkaMetricProducer;

    @Around("@annotation(metric)")
    public Object logMetricExecutionTime(ProceedingJoinPoint joinPoint, Metric metric) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.warn("Вызов метода: {}", joinPoint.getSignature());
        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            if (executionTime > metric.value()) {
                log.warn("Method was completed late. StartTime: {} ms. ExecutionTime: {} ms. PlannedTime: {} ms", startTime, executionTime, metric.value());
                MetricDto metricDto = MetricDto.builder()
                        .executionTime(executionTime)
                        .methodName(joinPoint.getSignature().getName())
                        .params(getMethodArgs(joinPoint.getArgs()))
                        .build();
                try {
                    kafkaMetricProducer.send(metricDto);
                } catch (Exception e) {
                    log.error("Kafka sending error: {}", e.getMessage());
                }
            }
        }
        return result;
    }

    private static String getMethodArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "No parameters";
        }
        StringBuilder params = new StringBuilder();
        for(Object arg : args) {
            if (!params.isEmpty()) {
                params.append(", ");
            }
            params.append(arg);
        }
        return params.toString();
    }
}
