package com.exam.digital.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ExamSubmissionDto {
    private Long examId;
    // Key = Question ID (Long), Value = Selected Answer (String)
    private Map<Long, String> answers;
}
