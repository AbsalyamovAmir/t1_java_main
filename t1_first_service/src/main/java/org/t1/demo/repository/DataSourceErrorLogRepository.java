package org.t1.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.t1.demo.model.DataSourceErrorLog;

/**
 * Репозиторий для работы с DataSourceErrorLog
 */
@Repository
public interface DataSourceErrorLogRepository extends JpaRepository<DataSourceErrorLog, Long> {

}
