package com.exam.digital.repository;

import com.exam.digital.model.Issue; import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByEmail(String email);
}