package org.t1.demo.service;

import org.t1.demo.model.dto.TransactionDto;
import org.t1.demo.model.enums.ClientStatus;

public interface RequestProcessingService {

    TransactionDto processRequest(TransactionDto transactionDto, ClientStatus clientStatus);
}
