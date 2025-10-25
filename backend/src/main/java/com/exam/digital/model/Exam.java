package com.exam.digital.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exams") // Match the table name you created
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Title is likely required
    private String title;

    @Column(columnDefinition = "TEXT") // Use TEXT for potentially longer descriptions
    private String description;

    @Column(nullable = false) // Scheduled time is required
    private LocalDateTime scheduled_time; // Use LocalDateTime for DATETIME SQL type

    @Column(nullable = false) // Time limit is required
    private Integer time_limit_minutes; // Use Integer for INT SQL type

    @Column(name = "created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // Let the DB handle default timestamp
}
