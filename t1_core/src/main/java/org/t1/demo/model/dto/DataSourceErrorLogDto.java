package org.t1.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSourceErrorLogDto implements Serializable {
    private String exceptionStackTrace;
    private String errorMessage;
    private String methodSignature;
}
