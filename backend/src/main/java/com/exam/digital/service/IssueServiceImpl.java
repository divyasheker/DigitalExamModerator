package com.exam.digital.service;

import com.exam.digital.model.Issue; import com.exam.digital.repository.IssueRepository; import com.exam.digital.service.IssueService; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Service;

import java.util.List;

@Service public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public Issue reportIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    @Override
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Override
    public List<Issue> getIssuesByEmail(String email) {
        return issueRepository.findByEmail(email);
    }
    @Override
    public Issue resolveIssue(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found with id: " + id));
        issue.setStatus("RESOLVED");
        return issueRepository.save(issue);
    }

}
