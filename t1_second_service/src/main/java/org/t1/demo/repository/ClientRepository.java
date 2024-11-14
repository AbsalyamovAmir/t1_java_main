package org.t1.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.t1.demo.model.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Override
    Optional<Client> findById(Long aLong);
}