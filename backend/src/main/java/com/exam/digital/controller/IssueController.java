package com.exam.digital.controller;

import com.exam.digital.model.Issue;
import com.exam.digital.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "*")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @PostMapping
    public Issue reportIssue(@RequestBody Issue issue) {
        issue.setTimestamp(LocalDateTime.now());
        return issueService.reportIssue(issue);
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @GetMapping("/my")
    public List<Issue> getMyIssues(@RequestParam String email) {
        return issueService.getIssuesByEmail(email);
    }
    @PutMapping("/{id}/resolve")
    public Issue resolveIssue(@PathVariable Long id) {
        return issueService.resolveIssue(id);
    }


}
