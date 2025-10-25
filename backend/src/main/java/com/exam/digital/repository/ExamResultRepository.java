package com.exam.digital.repository;

import com.exam.digital.model.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    List<ExamResult> findByStudentEmailOrderBySubmissionTimestampDesc(String studentEmail);
}
