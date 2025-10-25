package com.exam.digital.service; // Note the 'impl' subpackage

import com.exam.digital.model.Exam;
import com.exam.digital.repository.ExamRepository;
import com.exam.digital.service.ExamService;
import jakarta.persistence.EntityNotFoundException; // More specific exception
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    // Use constructor injection (recommended practice)
    @Autowired
    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Exam createExam(Exam exam) {
        // Add any validation or business logic before saving if needed
        return examRepository.save(exam);
    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Override
    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    @Override
    public Exam updateExam(Long id, Exam updatedExam) {
        // Find the existing exam or throw an exception
        Exam existingExam = examRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + id));

        // Update fields from the updatedExam object
        existingExam.setTitle(updatedExam.getTitle());
        existingExam.setDescription(updatedExam.getDescription());
        existingExam.setScheduled_time(updatedExam.getScheduled_time());
        existingExam.setTime_limit_minutes(updatedExam.getTime_limit_minutes());
        // createdAt is usually not updated

        return examRepository.save(existingExam);
    }

    @Override
    public void deleteExam(Long id) {
        // Check if exam exists before deleting to avoid errors if already deleted
        if (!examRepository.existsById(id)) {
            throw new EntityNotFoundException("Exam not found with id: " + id);
        }
        examRepository.deleteById(id);
    }
}
