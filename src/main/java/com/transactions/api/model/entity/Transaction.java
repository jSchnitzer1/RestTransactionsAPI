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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Account", referencedColumnName="accountId")
    private Account account;

    public Transaction() {
    }

    public Transaction(double transactionAmount, Account account) {
        this.transactionAmount = transactionAmount;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
