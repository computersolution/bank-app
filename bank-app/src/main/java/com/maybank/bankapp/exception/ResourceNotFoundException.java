package com.maybank.bankapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResourceNotFoundException is a custom exception class that represents the scenario where a requested resource is not found.
 * It is annotated with @ResponseStatus to automatically return a NOT_FOUND status code when thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message The detail message.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
