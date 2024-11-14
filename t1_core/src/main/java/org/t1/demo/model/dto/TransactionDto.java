package org.t1.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.t1.demo.model.Account;
import org.t1.demo.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO for {@link org.t1.demo.model.Transaction}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {
    private Long id;
    @JsonProperty("account")
    private Account account;
    @JsonProperty("sumTransaction")
    private BigDecimal sumTransaction;
    @JsonProperty("timeTransaction")
    private Date timeTransaction;
    @JsonProperty("transactionStatus")
    private TransactionStatus transactionStatus;
    @JsonProperty("transactionId")
    private long transactionId;
    @JsonProperty("timestamp")
    private Date timestamp;
}
