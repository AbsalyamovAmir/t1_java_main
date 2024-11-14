package org.t1.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.t1.demo.model.Account;

/**
 * Репозиторий для работы с сущностью Account
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
