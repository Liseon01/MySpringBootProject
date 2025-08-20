package com.rookies4.myspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter@Setter
@DynamicUpdate
public class Customer {
    // primary key, pk값을 persistence provider 결정
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // unique한 값을 가져야 하고, Null 값을 허용하지 않는다
    @Column(unique = true, nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAT = LocalDateTime.now();

}
