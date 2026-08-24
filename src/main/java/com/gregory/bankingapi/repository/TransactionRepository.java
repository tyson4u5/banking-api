package com.gregory.bankingapi.repository;

import com.gregory.bankingapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {
}
