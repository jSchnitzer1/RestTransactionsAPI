package com.transactions.api.model.dto;

import com.transactions.api.model.entity.Account;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * This DTO is used to prevent exposing the Account entity to the world.
 */
@XmlRootElement
public class AccountDTO {
    private int accountId;
    private double balance;

    public AccountDTO() {
    }

    public AccountDTO(int accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
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

    public static AccountDTO cloneFromEntity(Account account) {
        return new AccountDTO(account.getAccountId(), account.getBalance());
    }

    public static List<AccountDTO> cloneFromEntity(List<Account> accounts) {
        if(accounts == null || accounts.size() == 0) return null;
        List<AccountDTO> accountDTOs = new ArrayList<>();
        accounts.forEach(a -> accountDTOs.add(cloneFromEntity(a)));
        return accountDTOs;
    }
}