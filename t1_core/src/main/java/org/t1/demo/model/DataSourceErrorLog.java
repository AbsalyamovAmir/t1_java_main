package org.t1.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Date;

/**
 * Сущность Transaction
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "data_source_error_log")
@Builder
public class DataSourceErrorLog extends AbstractPersistable<Long> {

    /**
     * Текст стектрейса исключения
     */
    @Column(name = "exception_stack_trace")
    private String exceptionStackTrace;

    /**
     * Сообщение об ошибке
     */
    @Column(name = "error_message")
    private String errorMessage;

    /**
     * Сигнатура метода
     */
    @Column(name = "method_signature")
    private String methodSignature;
}
