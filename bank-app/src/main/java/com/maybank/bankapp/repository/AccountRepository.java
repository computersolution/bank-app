package com.maybank.bankapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.maybank.bankapp.entity.Account;

/**
 * The repository interface for managing Account entities.
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    /**
     * Retrieves an account by its unique identifier.
     *
     * @param id The unique identifier of the account.
     * @return An Optional containing the account if found, otherwise empty.
     */
    Optional<Account> findById(Long id);

    /**
     * Saves the provided account entity.
     *
     * @param account The account entity to be saved.
     * @return The saved account entity.
     */
    @SuppressWarnings("unchecked")
    Account save(Account account);
}

