package com.example.demo.repositories;

import com.example.demo.entities.Manager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @Transactional
    void deleteByEmail(String email);
    Optional<Manager> findByEmail(String email);
}
