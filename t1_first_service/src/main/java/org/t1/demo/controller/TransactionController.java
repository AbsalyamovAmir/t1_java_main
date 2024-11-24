package org.t1.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.t1.demo.aop.DataSourceErrorLogTrack;
import org.t1.demo.aop.Metric;
import org.t1.demo.model.dto.TransactionDto;
import org.t1.demo.model.enums.ClientStatus;
import org.t1.demo.service.RequestProcessingService;
import org.t1.demo.service.TransactionService;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final RestTemplate restTemplate;
    private final TransactionService transactionService;
    private final RequestProcessingService requestProcessingService;

    /**
     * Создание Transaction
     * @param transactionDto Dto для Transaction
     */
    @DataSourceErrorLogTrack
    @Metric(value = 1000)
    @PostMapping("/create")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        if (transactionDto.getAccount().getClient().getClientStatus() == null) {
            ResponseEntity<String> response = restTemplate.getForEntity(String.format("http://localhost:8081/client/check-status?clientId=%1$s&accountId=%2$s",
                    transactionDto.getAccount().getClient().getId(), transactionDto.getAccount().getId()), String.class);
            return ResponseEntity.ok(requestProcessingService.processRequest(transactionDto, ClientStatus.valueOf(response.getBody())));
        } else {
            return ResponseEntity.ok(transactionService.saveTransaction(transactionDto));
        }
    }

    /**
     * Генерация записей Transaction по заданному количеству
     * @param count Количество записей
     */
    @DataSourceErrorLogTrack
    @Metric(value = 1000)
    @PostMapping("/generate")
    public void generateTransactions(@RequestParam int count) {
        transactionService.generateTransactions(count);
    }

    /**
     * Получение Transaction по id
     * @param id идентификатор Transaction
     * @return Dto для Transaction
     */
    @DataSourceErrorLogTrack
    @Metric(value = 1000)
    @GetMapping("/{id}")
    public TransactionDto getTransaction(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    /**
     * Метод для тестирования @DataSourceErrorLogTrack
     * @throws RuntimeException Исключение для тестирования
     */
    @DataSourceErrorLogTrack
    @Metric(value = 1000)
    @GetMapping("/test-error")
    public void testError() throws Exception {
        Thread.sleep(1500);
        throw new Exception("Тестовое исключение для проверки логирования Transaction.");
    }

    @PostMapping("/sendTransaction")
    public void sendTransaction(@RequestBody TransactionDto transactionDto) {
        transactionService.sendTransaction(transactionDto);
    }
}
