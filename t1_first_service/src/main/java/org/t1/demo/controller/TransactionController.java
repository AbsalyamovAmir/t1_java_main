package org.t1.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.t1.demo.aop.DataSourceErrorLogTrack;
import org.t1.demo.aop.Metric;
import org.t1.demo.model.dto.TransactionDto;
import org.t1.demo.service.TransactionService;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Создание Transaction
     * @param transactionDto Dto для Transaction
     */
    @DataSourceErrorLogTrack
    @Metric(value = 1000)
    @PostMapping
    public void createTransaction(@RequestBody TransactionDto transactionDto) {
        transactionService.saveTransaction(transactionDto);
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
