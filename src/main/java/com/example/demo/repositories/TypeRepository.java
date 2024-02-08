package com.example.demo.repositories;

import com.example.demo.entities.Type;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    @Transactional
    void deleteByName(String name);
    Optional<Type> findByName(String name);
}
