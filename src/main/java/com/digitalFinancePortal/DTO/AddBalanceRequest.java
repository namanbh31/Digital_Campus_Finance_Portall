package com.digitalFinancePortal.DTO;

public class AddBalanceRequest {
    private Long accountNumber;
    private Long balance;
    private Boolean readOnly;

    public AddBalanceRequest(Long accountNumber, Long balance, Boolean readOnly) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.readOnly = readOnly;
    }

    @Override
    public String toString() {
        return "AddBalanceRequest{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", readOnly=" + readOnly +
                '}';
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public AddBalanceRequest() {
    }


    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
