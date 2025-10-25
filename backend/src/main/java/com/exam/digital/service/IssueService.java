package com.exam.digital.service;

import com.exam.digital.model.Issue;

import java.util.List;

public interface IssueService {
    Issue reportIssue(Issue issue);
    List<Issue> getAllIssues();
    List<Issue> getIssuesByEmail(String email);
    Issue resolveIssue(Long id);
}