package com.transactions.api.database;

import com.transactions.api.model.dto.TransactionDTO;

import java.util.List;

public interface DatabaseManager {
    void initDatabase();
    void destroyDatabase();
    TransactionStatus createTransaction(int accountId, double transactionAmount, String transactionUUID);
    TransactionStatus deleteTransaction(int accountId, String transactionUUID);
    List<TransactionDTO> getTransactions(int accountId);
}
