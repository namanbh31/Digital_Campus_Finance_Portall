package com.digitalFinancePortal.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="transaction_info")

public class TransactionRecorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long amount;
    private long senderAccount;
    private long receiverAccount;
    private LocalDateTime timestamp;
    private String description;
    public TransactionRecorder() {

    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }
    public long getSenderAccount() {
        return senderAccount;
    }
    public void setSenderAccount(long senderAccount) {
        this.senderAccount = senderAccount;
    }
    public long getReceiverAccount() {
        return receiverAccount;
    }
    public void setReceiverAccount(long receiverAccount) {
        this.receiverAccount = receiverAccount;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}