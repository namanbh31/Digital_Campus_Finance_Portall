package com.digitalFinancePortal.entities;

import jakarta.persistence.*;

@Entity
@Table(name="user_info")
public class User {


    @Id
    private Long accountNumber;
    private String password;
    private String name;
    private String college;
    private Long balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    public User(Long accountNumber, String name, String password, String college,
                long balance, UserType userType) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.college = college;
        this.password = password;
        this.userType = userType;
    }
    public User() {

    }
    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getAccountNumber() {
        return accountNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCollege() {
        return college;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    public Long getBalance() {
        return balance;
    }
    public void setBalance(Long balance) {
        this.balance = balance;
    }
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    @Override
    public String toString() {
        return "name="+this.name+", accountNumber="+this.accountNumber+", balance="+this.balance+", college="+this.college
                +", userType="+ this.userType;
    }

}
