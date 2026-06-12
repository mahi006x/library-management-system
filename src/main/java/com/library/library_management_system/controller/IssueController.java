package com.library.library_management_system.controller;

import com.library.library_management_system.model.IssueRecord;
import com.library.library_management_system.model.User;
import com.library.library_management_system.repository.UserRepository;
import com.library.library_management_system.service.IssueService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "http://localhost:5173")
public class IssueController {

    private final IssueService issueService;
    private final UserRepository userRepository;

    public IssueController(
            IssueService issueService,
            UserRepository userRepository
    ) {
        this.issueService = issueService;
        this.userRepository = userRepository;
    }

    @PostMapping("/request/{bookId}")
    public IssueRecord requestIssue(@PathVariable Long bookId) {
        Long testUserId = 2L;
        return issueService.requestIssue(testUserId, bookId);
    }

    @PutMapping("/approve/{id}")
    public IssueRecord approveIssue(@PathVariable Long id) {
        return issueService.approveIssue(id);
    }

    @PutMapping("/return/{id}")
    public IssueRecord returnBook(@PathVariable Long id) {
        return issueService.returnBook(id);
    }

    @GetMapping("/my")
    public List<IssueRecord> myIssues() {
        Long testUserId = 2L;
        return issueService.getUserIssues(testUserId);
    }

    @GetMapping("/pending")
    public List<IssueRecord> pendingIssues() {
        return issueService.getPendingIssues();
    }

    @GetMapping("/active")
    public List<IssueRecord> activeIssues() {
        return issueService.getActiveIssues();
    }
}