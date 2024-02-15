package com.example.demo.repositories;

import com.example.demo.entities.Brand;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Transactional
    void deleteByName(String name);
    Optional<Brand> findByName(String name);
}
