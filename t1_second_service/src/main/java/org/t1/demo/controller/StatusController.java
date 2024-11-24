package org.t1.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.t1.demo.model.dto.TransactionDto;
import org.t1.demo.model.enums.ClientStatus;

import java.util.Random;

@RestController
@RequestMapping("/status")
public class StatusController {

    @GetMapping("/check")
    public ResponseEntity<ClientStatus> checkClientStatus(@RequestBody TransactionDto transactionDto) {
        boolean isBlocked = checkIfBlocked(transactionDto.getAccount().getClient().getClientId());
        return ResponseEntity.ok(isBlocked ? ClientStatus.BLOCKED : ClientStatus.ACTIVE);
    }

    private boolean checkIfBlocked(long clientId) {
        return new Random().nextInt(100) < 20;
    }
}
