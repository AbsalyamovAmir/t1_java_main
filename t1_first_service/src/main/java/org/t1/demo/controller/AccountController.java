package org.t1.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.t1.demo.aop.DataSourceErrorLogTrack;
import org.t1.demo.aop.Metric;
import org.t1.demo.model.dto.AccountDto;
import org.t1.demo.service.AccountService;

/**
 * Контроллер для работы с сущностью Account
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    /**
     * Создание Account
     * @param accountDto Dto для Account
     */
    @DataSourceErrorLogTrack
    @Metric(value = 1000)
    @PostMapping
    public void createAccount(@RequestBody AccountDto accountDto) {
        accountService.saveAccount(accountDto);
    }

    /**
     * Генерация записей Account по заданному количеству
     * @param count Количество записей
     */
    @DataSourceErrorLogTrack
    @Metric(value = 1000)
    @PostMapping("/generate")
    public void generateAccounts(@RequestParam int count) {
        accountService.generateAccounts(count);
    }

    /**
     * Получение Account по id
     * @param id идентификатор Account
     * @return Dto для Account
     */
    @DataSourceErrorLogTrack
    @Metric(value = 1000)
    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    /**
     * Метод для тестирования @DataSourceErrorLogTrack
     * @throws RuntimeException Исключение для тестирования
     */
    @DataSourceErrorLogTrack
    @Metric(value = 1000)
    @GetMapping("/test-error")
    public void testError() throws Exception {
        Thread.sleep(15000);
        throw new Exception("Тестовое исключение для проверки логирования Transaction.");
    }

    @PostMapping("/sendAccount")
    public void sendAccount(@RequestBody AccountDto accountDto) {
        accountService.sendAccount(accountDto);
    }
}
