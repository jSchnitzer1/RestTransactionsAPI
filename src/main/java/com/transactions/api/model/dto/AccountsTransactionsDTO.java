package com.transactions.api.model.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class AccountsTransactionsDTO {
    private CustomerDTO customerDTO;
    private List<AccountDTO> accountDTOs;
    private List<TransactionDTO> transactionDTOs;

    public AccountsTransactionsDTO() {
    }

    public AccountsTransactionsDTO(CustomerDTO customerDTO, List<AccountDTO> accountDTOs, List<TransactionDTO> transactionDTOs) {
        this.customerDTO = customerDTO;
        this.accountDTOs = accountDTOs;
        this.transactionDTOs = transactionDTOs;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public List<AccountDTO> getAccountDTOs() {
        return accountDTOs;
    }

    public void setAccountDTOs(List<AccountDTO> accountDTOs) {
        this.accountDTOs = accountDTOs;
    }

    public List<TransactionDTO> getTransactionDTOs() {
        return transactionDTOs;
    }

    public void setTransactionDTOs(List<TransactionDTO> transactionDTOs) {
        this.transactionDTOs = transactionDTOs;
    }
}