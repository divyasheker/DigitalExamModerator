package com.exam.digital.controller;

import com.exam.digital.model.Exam;
import com.exam.digital.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Use ResponseEntity for better control over responses
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams") // Base path for exam APIs
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access (adjust port if needed)
public class ExamController {

    private final ExamService examService;

    // Use constructor injection
    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    // Create a new exam
    @PostMapping("/add")
    public ResponseEntity<Exam> addExam(@RequestBody Exam exam) {
        Exam createdExam = examService.createExam(exam);
        return ResponseEntity.ok(createdExam); // Return 200 OK with the created exam
    }

    // Get all exams
    @GetMapping("/all")
    public ResponseEntity<List<Exam>> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        return ResponseEntity.ok(exams); // Return 200 OK with the list
    }

    // Get a single exam by ID (Optional, maybe needed later)
    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Long id) {
        return examService.getExamById(id)
                .map(ResponseEntity::ok) // If found, return 200 OK with exam
                .orElse(ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }


    // Update an existing exam
    @PutMapping("/update/{id}")
    public ResponseEntity<Exam> updateExam(@PathVariable Long id, @RequestBody Exam updatedExam) {
        try {
            Exam exam = examService.updateExam(id, updatedExam);
            return ResponseEntity.ok(exam);
        } catch (jakarta.persistence.EntityNotFoundException e) { // Catch specific exception
            return ResponseEntity.notFound().build(); // Return 404 if exam not found
        }
    }

    // Delete an exam
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        try {
            examService.deleteExam(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content on successful delete
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return 404 if exam not found
        }
    }
}
