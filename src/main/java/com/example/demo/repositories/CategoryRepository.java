package com.example.demo.repositories;

import com.example.demo.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Transactional
    void deleteByName(String name);
    Optional<Category> findByName(String name);
}
