package com.library.library_management_system.repository;

import com.library.library_management_system.model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {

    List<Fine> findByIssueRecordUserIdAndPaidFalse(Long userId);
    @Query("""
    		SELECT COALESCE(SUM(f.amount),0)
    		FROM Fine f
    		WHERE f.paid = true
    		""")
    		Double getTotalCollectedFines();
}