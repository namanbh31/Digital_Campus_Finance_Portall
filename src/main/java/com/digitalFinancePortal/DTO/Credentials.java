package com.digitalFinancePortal.DTO;

public class Credentials {
    private String accountNumber;
    private String password;

    public Credentials(String accountNumber, String password) {
        this.accountNumber = accountNumber;
        this.password = password;
    }

    public Credentials() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "accountNumber='" + accountNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
