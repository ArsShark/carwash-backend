package com.example.carwash.repository;

import com.example.carwash.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // @SQLRestriction on the entity automatically filters out deleted = false
}