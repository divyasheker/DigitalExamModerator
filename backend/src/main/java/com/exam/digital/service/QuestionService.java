package com.exam.digital.service;

import com.exam.digital.model.Question;
import java.util.List;
import java.util.Optional;

public interface QuestionService {

    Question createQuestion(Question question);

    Question addQuestion(Question question);

    List<Question> getAllQuestions();

    Optional<Question> getQuestionById(Long id);

    Question updateQuestion(Long id, Question updatedQuestion);

    void deleteQuestion(Long id);
}
