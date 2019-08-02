package com.transactions.api.model.dto;

import com.transactions.api.model.entity.Customer;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This DTO is used to prevent exposing the Customer entity to the world.
 */
@XmlRootElement
public class CustomerDTO {
    private int customerId;
    private String firstName;
    private String lastName;

    public CustomerDTO() {
    }

    public CustomerDTO(int customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public static CustomerDTO cloneFromEntity(Customer customer) {
        return new CustomerDTO(customer.getCustomerId(), customer.getFirstName(), customer.getLastName());
    }
}