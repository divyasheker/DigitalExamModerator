package com.exam.digital.service.impl;

import com.exam.digital.dto.ExamSubmissionDto;
import com.exam.digital.model.Exam;
import com.exam.digital.model.ExamResult;
import com.exam.digital.model.Question;
import com.exam.digital.repository.ExamRepository;
import com.exam.digital.repository.ExamResultRepository;
import com.exam.digital.repository.QuestionRepository;
import com.exam.digital.service.ExamResultService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExamResultServiceImpl implements ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    @Autowired
    public ExamResultServiceImpl(ExamResultRepository examResultRepository,
                                 QuestionRepository questionRepository,
                                 ExamRepository examRepository) {
        this.examResultRepository = examResultRepository;
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
    }

    @Override
    @Transactional
    public ExamResult evaluateAndSaveResult(ExamSubmissionDto submissionDto, String studentEmail) throws EntityNotFoundException {
        if (submissionDto == null || submissionDto.getExamId() == null || submissionDto.getAnswers() == null) {
            throw new IllegalArgumentException("Invalid submission data: examId and answers are required.");
        }

        Long examId = submissionDto.getExamId();
        Map<Long, String> studentAnswers = submissionDto.getAnswers();

        // Fetch the Exam for title validation
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with ID: " + examId));

        // Fetch all questions relevant for evaluation
        // Using findAll based on the assumption all questions apply.
        // If specific questions per exam were needed, this logic would change.
        List<Question> allQuestions = questionRepository.findAll();
        if (allQuestions.isEmpty()) {
            // If there are absolutely no questions, we can't score anything.
            // Save a result indicating this unusual state.
            return examResultRepository.save(ExamResult.builder()
                    .examId(exam.getId())
                    .examTitle(exam.getTitle())
                    .studentEmail(studentEmail)
                    .score(0)
                    .totalQuestions(0) // No questions to score against
                    .submissionTimestamp(LocalDateTime.now())
                    .build());
        }

        // Map correct answers for quick lookup
        Map<Long, String> correctAnswersMap = allQuestions.stream()
                .collect(Collectors.toMap(Question::getId, Question::getCorrectAnswer, (existing, replacement) -> existing)); // Handle potential duplicate keys if needed

        // Evaluate score
        int score = 0;
        for (Question question : allQuestions) {
            Long questionId = question.getId();
            String correctAnswer = correctAnswersMap.get(questionId);
            String studentAnswer = studentAnswers.get(questionId); // Get student's answer for this question

            // Only compare if a correct answer exists and the student provided an answer
            if (correctAnswer != null && studentAnswer != null) {
                // Trim whitespace and compare (case-sensitive for now, adjust if needed)
                if (studentAnswer.trim().equals(correctAnswer.trim())) {
                    score++;
                }
            }
            // If student didn't answer (studentAnswer is null), they don't get points.
        }

        // Total questions is the count of questions fetched for evaluation
        int totalQuestions = allQuestions.size();

        // Build and save the result
        ExamResult result = ExamResult.builder()
                .examId(exam.getId())
                .examTitle(exam.getTitle())
                .studentEmail(studentEmail)
                .score(score)
                .totalQuestions(totalQuestions)
                // Let @PrePersist handle timestamp: .submissionTimestamp(LocalDateTime.now())
                .build();

        return examResultRepository.save(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResult> getResultsForStudent(String studentEmail) {
        if (studentEmail == null || studentEmail.isBlank()) {
            throw new IllegalArgumentException("Student email is required to fetch results.");
        }
        return examResultRepository.findByStudentEmailOrderBySubmissionTimestampDesc(studentEmail);
    }
}
