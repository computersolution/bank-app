package com.maybank.bankapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Customer entity represents a bank customer.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

    /**
     * Unique identifier for the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the customer.
     */
    private String name;

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the customer.
     *
     * @return The unique identifier of the customer.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the customer.
     *
     * @param id The unique identifier of the customer.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name The name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }
}
