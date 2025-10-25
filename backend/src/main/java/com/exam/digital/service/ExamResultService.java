package com.exam.digital.service;

import com.exam.digital.dto.ExamSubmissionDto;
import com.exam.digital.model.ExamResult;
import jakarta.persistence.EntityNotFoundException; // Correct import

import java.util.List;

public interface ExamResultService {

    ExamResult evaluateAndSaveResult(ExamSubmissionDto submissionDto, String studentEmail) throws EntityNotFoundException;

    List<ExamResult> getResultsForStudent(String studentEmail);
}
