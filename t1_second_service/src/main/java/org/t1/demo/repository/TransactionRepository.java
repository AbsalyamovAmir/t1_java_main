package org.t1.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.t1.demo.model.Transaction;

/**
 * Репозиторий для работы с сущностью Transaction
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
