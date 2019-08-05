package com.transactions.api.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Transaction")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private int transactionId;

    @Column
    private double transactionAmount;

    @Column
    private int accountId;

    public Transaction() {
    }

    public Transaction(double transactionAmount, int account) {
        this.transactionAmount = transactionAmount;
        this.accountId = account;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
