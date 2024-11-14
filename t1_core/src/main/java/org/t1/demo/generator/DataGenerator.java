package org.t1.demo.generator;

import org.t1.demo.model.Account;
import org.t1.demo.model.Client;
import org.t1.demo.model.Transaction;
import org.t1.demo.model.enums.AccountType;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Класс-генератор данных
 */
public class DataGenerator {

    private static final Random random = new Random();

    /**
     * Генератор для Account
     * @param count Необходимое количество записей
     * @return      Список объектов Account
     */
    public static List<Account> generateAccounts(int count, List<Client> clients) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Account account = new Account();
            account.setClient(clients.get(random.nextInt(clients.size())));
            account.setAccountType(random.nextBoolean() ? AccountType.DEBIT : AccountType.CREDIT);
            account.setBalance(BigDecimal.valueOf(random.nextDouble())
                    .multiply(BigDecimal.valueOf((10000 - 100)))
                    .add(BigDecimal.valueOf(100)));
            accounts.add(account);
        }
        return accounts;
    }

    /**
     * Генератор для Transaction
     * @param count Необходимое количество записей
     * @return      Список объектов Transaction
     */
    public static List<Transaction> generateTransactions(int count, List<Account> accounts) {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Transaction transaction = new Transaction();
            transaction.setAccount(accounts.get(random.nextInt(accounts.size())));
            transaction.setSumTransaction(BigDecimal.valueOf(random.nextDouble())
                    .multiply(BigDecimal.valueOf((10000 - 1)))
                    .add(BigDecimal.valueOf(1)));
            long currentTimeMillis = System.currentTimeMillis();
            long pastTimeMillis = currentTimeMillis - TimeUnit.DAYS.toMillis(random.nextInt(30));
            Date transactionTime = new Date(pastTimeMillis);
            transaction.setTimeTransaction(transactionTime);
            transactions.add(transaction);
        }
        return transactions;
    }
}
