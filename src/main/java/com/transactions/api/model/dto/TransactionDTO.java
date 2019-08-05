package com.transactions.api.model.dto;

import com.transactions.api.model.entity.Transaction;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * This DTO is used to prevent exposing the Transaction entity to the world.
 */
@XmlRootElement
public class TransactionDTO {
    private int transactionId;
    private double transactionAmount;
    private int accountId;

    public TransactionDTO() {
    }

    public TransactionDTO(int transactionId, double transactionAmount) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public int getAccountId() {
        return accountId;
    }

//    public static TransactionDTO cloneFromEntity(Transaction transaction) {
//        return new TransactionDTO(transaction.getTransactionId(), transaction.getTransactionAmount());
//    }
//
//    public static List<TransactionDTO> cloneFromEntity(List<Transaction> transactions) {
//        if(transactions == null || transactions.size() == 0) return null;
//        List<TransactionDTO> transactionDTOs = new ArrayList<>();
//        transactions.forEach(tx -> transactionDTOs.add(cloneFromEntity(tx)));
//        return transactionDTOs;
//    }
}
