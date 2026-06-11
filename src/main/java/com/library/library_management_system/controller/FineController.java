package com.library.library_management_system.controller;

import com.library.library_management_system.model.Fine;
import com.library.library_management_system.service.FineService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fines")
@CrossOrigin(origins = "http://localhost:5173")
public class FineController {

    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping("/my")
    public List<Fine> myFines() {
        Long testUserId = 2L;
        return fineService.getMyUnpaidFines(testUserId);
    }

    @PutMapping("/pay/{id}")
    public Fine payFine(@PathVariable Long id) {
        return fineService.payFine(id);
    }
}