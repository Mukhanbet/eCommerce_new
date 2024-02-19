package com.example.demo.entities;

import com.example.demo.enums.StatusDiscount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Discount {
    // todo think about type when discount will be 1 + 1 == 3 and others also
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int discount;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private StatusDiscount status;

}
