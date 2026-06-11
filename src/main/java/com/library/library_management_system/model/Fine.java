package com.library.library_management_system.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fines")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private IssueRecord issueRecord;

    private Double amount;
    private Boolean paid;
    private LocalDate paidDate;

    public Fine() {
    }

    public Long getId() {
        return id;
    }

    public IssueRecord getIssueRecord() {
        return issueRecord;
    }

    public Double getAmount() {
        return amount;
    }

    public Boolean getPaid() {
        return paid;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIssueRecord(IssueRecord issueRecord) {
        this.issueRecord = issueRecord;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }
}