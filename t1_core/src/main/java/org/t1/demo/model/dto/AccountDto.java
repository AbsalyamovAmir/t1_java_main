package org.t1.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.t1.demo.model.Client;
import org.t1.demo.model.enums.AccountStatus;
import org.t1.demo.model.enums.AccountType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link org.t1.demo.model.Account}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto implements Serializable {
    private Long id;
    @JsonProperty("client")
    private Client client;
    @JsonProperty("account_type")
    private AccountType accountType;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("accountStatus")
    private AccountStatus accountStatus;
    @JsonProperty("accountId")
    private long accountId;
    @JsonProperty("frozenAmount")
    private BigDecimal frozenAmount;
}
