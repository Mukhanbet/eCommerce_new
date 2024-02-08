package com.example.demo.repositories;

import com.example.demo.entities.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Transactional
    void deleteByEmail(String email);

    Optional<Employee> findByEmail(String email);
}
