package com.library.library_management_system.service;

import com.library.library_management_system.model.Book;
import com.library.library_management_system.model.Fine;
import com.library.library_management_system.model.IssueRecord;
import com.library.library_management_system.model.IssueStatus;
import com.library.library_management_system.model.User;

import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.FineRepository;
import com.library.library_management_system.repository.IssueRepository;
import com.library.library_management_system.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final FineRepository fineRepository;

    public IssueService(
            IssueRepository issueRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            FineRepository fineRepository
    ) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.fineRepository = fineRepository;
    }

    public IssueRecord requestIssue(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        IssueRecord issueRecord = new IssueRecord(
                user,
                book,
                LocalDate.now(),
                LocalDate.now().plusDays(14),
                null,
                IssueStatus.PENDING
        );

        return issueRepository.save(issueRecord);
    }

    public IssueRecord approveIssue(Long issueId) {

        IssueRecord issueRecord = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue record not found"));

        if (issueRecord.getStatus() != IssueStatus.PENDING) {
            throw new RuntimeException("Only pending requests can be approved");
        }

        Book book = issueRecord.getBook();

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book is not available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        issueRecord.setStatus(IssueStatus.APPROVED);
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(14));

        return issueRepository.save(issueRecord);
    }

    public IssueRecord returnBook(Long issueId) {

        IssueRecord issueRecord = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue record not found"));

        if (issueRecord.getStatus() != IssueStatus.APPROVED &&
                issueRecord.getStatus() != IssueStatus.OVERDUE) {
            throw new RuntimeException("Only approved or overdue books can be returned");
        }

        Book book = issueRecord.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        issueRecord.setStatus(IssueStatus.RETURNED);
        issueRecord.setReturnDate(LocalDate.now());

        IssueRecord savedIssue = issueRepository.save(issueRecord);

        long daysOverdue = ChronoUnit.DAYS.between(
                issueRecord.getDueDate(),
                LocalDate.now()
        );

        if (daysOverdue > 0) {
            Fine fine = new Fine();
            fine.setIssueRecord(savedIssue);
            fine.setAmount(daysOverdue * 2.0);
            fine.setPaid(false);
            fineRepository.save(fine);
        }

        return savedIssue;
    }

    public List<IssueRecord> getUserIssues(Long userId) {
        return issueRepository.findByUserId(userId);
    }

    public List<IssueRecord> getPendingIssues() {
        return issueRepository.findByStatus(IssueStatus.PENDING);
    }

    public List<IssueRecord> getActiveIssues() {
        return issueRepository.findByStatus(IssueStatus.APPROVED);
    }

    public List<IssueRecord> getOverdueIssues() {
        return issueRepository.findByDueDateBeforeAndStatus(
                LocalDate.now(),
                IssueStatus.APPROVED
        );
    }
}