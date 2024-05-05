package com.maybank.bankapp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Account entity represents a bank account.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    /**
     * Unique identifier for the account.
     */
    @Id
    private Long id;

    /**
     * Type of the account (e.g., savings, checking).
     */
    private String type;

    /**
     * Current balance of the account.
     */
    private double balance;

    /**
     * Status of the account (e.g., Active, Closed).
     */
    private String status;

    /**
     * Customer associated with the account.
     */
    @ManyToOne
    private Customer customer;

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the account.
     *
     * @return The unique identifier of the account.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the account.
     *
     * @param id The unique identifier of the account.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the type of the account.
     *
     * @return The type of the account.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the account.
     *
     * @param type The type of the account.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the current balance of the account.
     *
     * @return The current balance of the account.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the current balance of the account.
     *
     * @param balance The current balance of the account.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Retrieves the status of the account.
     *
     * @return The status of the account.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the account.
     *
     * @param status The status of the account.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the customer associated with the account.
     *
     * @return The customer associated with the account.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with the account.
     *
     * @param customer The customer associated with the account.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
