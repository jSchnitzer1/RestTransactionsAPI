package com.transactions.api.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private int customerId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    //mappedby = Account is not required in this entity because it can automatically find it
    @JoinColumn(name="Customer", referencedColumnName= "customerId")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accountsRecords;

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(String firstName, String lastName, List<Account> accountsRecords) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountsRecords = accountsRecords;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Account> getAccountsRecords() {
        return accountsRecords;
    }

    public void setAccountsRecords(List<Account> accountsRecords) {
        this.accountsRecords = accountsRecords;
    }
}