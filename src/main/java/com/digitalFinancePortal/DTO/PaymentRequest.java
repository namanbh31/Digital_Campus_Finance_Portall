package com.digitalFinancePortal.DTO;

public class PaymentRequest {
    private Long accountNumber;
    private String password;
    private Long amount;

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "accountNumber=" + accountNumber +
                ", password='" + password + '\'' +
                ", amount=" + amount +
                '}';
    }

    public PaymentRequest() {
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}