package com.transactions.api.database;

public class TransactionStatus {
    public enum TransactionResult {
        SUCCESS,
        ERROR
    }

    private TransactionResult result;
    private String message;

    public TransactionStatus() {
    }

    public TransactionStatus(TransactionResult result, String message) {
        this.result = result;
        this.message = message;
    }

    public TransactionResult getResult() {
        return result;
    }

    public void setResult(TransactionResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
