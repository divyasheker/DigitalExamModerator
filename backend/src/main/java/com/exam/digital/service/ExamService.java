package com.exam.digital.service;

import com.exam.digital.model.Exam;
import java.util.List;
import java.util.Optional;

public interface ExamService {

    Exam createExam(Exam exam);

    List<Exam> getAllExams();

    Optional<Exam> getExamById(Long id); // Optional for better handling if not found

    Exam updateExam(Long id, Exam updatedExam);

    void deleteExam(Long id);
}
