package com.exam.digital.controller;

import com.exam.digital.dto.ExamSubmissionDto;
import com.exam.digital.model.ExamResult;
import com.exam.digital.model.User; // *** Import your User entity ***
import com.exam.digital.service.ExamResultService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.core.context.SecurityContextHolder; // Import SecurityContextHolder
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

// Removed Principal import as we'll use SecurityContextHolder

import java.util.List;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "http://localhost:3000") // Adjust if needed
public class ExamResultController {

    private final ExamResultService examResultService;

    @Autowired
    public ExamResultController(ExamResultService examResultService) {
        this.examResultService = examResultService;
    }

    // Helper method to get student email from Security Context
    private String getAuthenticatedStudentEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
            return null; // Not authenticated or principal not set
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            // If your JwtAuthFilter stores the User entity directly
            return ((User) principal).getEmail();
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            // If your filter stores UserDetails (less likely based on your filter code)
            return ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            // If the principal is just the email string
            return (String) principal;
        }

        // Fallback or error if principal type is unexpected
        System.err.println("Unexpected principal type: " + principal.getClass().getName());
        return null;
    }


    // Endpoint to submit exam answers
    @PostMapping("/submit")
    public ResponseEntity<ExamResult> submitExam(@RequestBody ExamSubmissionDto submissionDto) {
        String studentEmail = getAuthenticatedStudentEmail();

        if (studentEmail == null || studentEmail.isBlank()) {
            // User is not properly authenticated or email couldn't be retrieved
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Send 401
        }

        try {
            ExamResult result = examResultService.evaluateAndSaveResult(submissionDto, studentEmail);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            // Use ResponseStatusException to let Spring handle error response
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            // Log the unexpected error for debugging
            System.err.println("Unexpected error during exam submission: " + e.getMessage());
            e.printStackTrace(); // Consider using a proper logger
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing exam submission.", e);
        }
    }

    // Endpoint to fetch results for the logged-in student
    @GetMapping("/student")
    public ResponseEntity<List<ExamResult>> getStudentResults() {
        String studentEmail = getAuthenticatedStudentEmail();

        if (studentEmail == null || studentEmail.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Send 401
        }

        try {
            List<ExamResult> results = examResultService.getResultsForStudent(studentEmail);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            // Usually, studentEmail validation is internal, but handle just in case
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("Unexpected error fetching results: " + e.getMessage());
            e.printStackTrace(); // Consider using a proper logger
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching results.", e);
        }
    }
}
