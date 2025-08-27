package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentNumber(String studentNumber);
    
    @Query("SELECT s FROM Student s JOIN FETCH s.studentDetail WHERE s.id = :id")
    Optional<Student> findByIdWithStudentDetail(@Param("id") Long id);
    
    boolean existsByStudentNumber(String studentNumber);
}