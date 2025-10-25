package com.exam.digital.repository;

import com.exam.digital.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    // JpaRepository provides CRUD methods automatically
    // Custom query methods can be added here later if needed
}
