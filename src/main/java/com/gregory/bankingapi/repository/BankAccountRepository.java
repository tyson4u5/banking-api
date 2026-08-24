package com.gregory.bankingapi.repository;

import com.gregory.bankingapi.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository
        extends JpaRepository<BankAccount, Long> {
}