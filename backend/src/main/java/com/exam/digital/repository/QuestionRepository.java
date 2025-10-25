package com.exam.digital.repository;

import com.exam.digital.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // You can add custom query methods later if needed
    Optional<Question> findById(Long id);
}
