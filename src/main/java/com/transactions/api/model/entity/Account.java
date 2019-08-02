package com.transactions.api.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
    private int accountId;

    @Column
    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Customer", referencedColumnName="customerId")
    private Customer customer;

    //mappedby = Account is not required in this entity because it can automatically find it
    @JoinColumn(name="Account", referencedColumnName= "accountId")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactionRecords;

    public Account() {
    }

    public Account(double balance, Customer customer) {
        this.balance = balance;
        this.customer = customer;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Transaction> getTransactionRecords() {
        return transactionRecords;
    }

    public void setTransactionRecords(List<Transaction> transactionRecords) {
        this.transactionRecords = transactionRecords;
    }
}
