package com.exam.digital.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exam_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long examId;

    @Column(nullable = false)
    private String examTitle;

    @Column(nullable = false)
    private String studentEmail;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Integer totalQuestions;

    @Column(nullable = false, updatable = false) // Timestamp set on creation
    private LocalDateTime submissionTimestamp;

    @PrePersist
    protected void onCreate() {
        submissionTimestamp = LocalDateTime.now();
    }
}
