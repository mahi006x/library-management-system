package com.library.library_management_system.service;

import com.library.library_management_system.model.Fine;
import com.library.library_management_system.repository.FineRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FineService {

    private final FineRepository fineRepository;

    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public List<Fine> getMyUnpaidFines(Long userId) {
        return fineRepository.findByIssueRecordUserIdAndPaidFalse(userId);
    }

    public Fine payFine(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new RuntimeException("Fine not found"));

        fine.setPaid(true);
        fine.setPaidDate(LocalDate.now());

        return fineRepository.save(fine);
    }
}