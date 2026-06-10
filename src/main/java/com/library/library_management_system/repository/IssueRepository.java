package com.library.library_management_system.repository;

import com.library.library_management_system.model.IssueRecord;
import com.library.library_management_system.model.IssueStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IssueRepository extends JpaRepository<IssueRecord, Long> {

    List<IssueRecord> findByUserId(Long userId);

    List<IssueRecord> findByStatus(IssueStatus status);

    List<IssueRecord> findByDueDateBeforeAndStatus(
            LocalDate date,
            IssueStatus status
    );
}