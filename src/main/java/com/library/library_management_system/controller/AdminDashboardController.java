package com.library.library_management_system.controller;

import com.library.library_management_system.model.IssueStatus;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.FineRepository;
import com.library.library_management_system.repository.IssueRepository;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminDashboardController {

    private final BookRepository bookRepository;
    private final IssueRepository issueRepository;
    private final FineRepository fineRepository;

    public AdminDashboardController(
            BookRepository bookRepository,
            IssueRepository issueRepository,
            FineRepository fineRepository) {

        this.bookRepository = bookRepository;
        this.issueRepository = issueRepository;
        this.fineRepository = fineRepository;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboardStats() {

        Map<String, Object> stats = new HashMap<>();

        stats.put("totalBooks", bookRepository.count());

        stats.put(
                "activeIssues",
                issueRepository.findByStatus(IssueStatus.APPROVED).size()
        );

        stats.put(
                "overdueCount",
                issueRepository.getOverdueIssuesCount()
        );

        stats.put(
                "finesCollected",
                fineRepository.getTotalCollectedFines()
        );

        return stats;
    }
}