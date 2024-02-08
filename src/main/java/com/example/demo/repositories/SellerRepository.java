package com.example.demo.repositories;

import com.example.demo.entities.Seller;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Transactional
    void deleteByEmail(String email);
    Optional<Seller> findByEmail(String email);
}
