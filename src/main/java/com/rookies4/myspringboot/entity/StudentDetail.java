package com.rookies4.myspringboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//StudentDetail 클래스
@Entity
@Table(name = "student_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_detail_id")
    private Long id;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column
    private LocalDate dateOfBirth;

    // @JoinColumn 외래키에 해당하는 어노테이션
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", unique = true)
    private Student student;
}