package com.digitalFinancePortal.entities;

import jakarta.persistence.*;
import org.springframework.context.annotation.Description;

@Entity
@Table(name = "dues_info")
public class Dues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountNumber;
    private Long amount;
    private String description;

    public Dues() {
    }

    public Dues(Long id, Long accountNumber, Long amount, String description) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Dues{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
