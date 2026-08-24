package com.gregory.bankingapi.service;

import com.gregory.bankingapi.model.BankAccount;
import com.gregory.bankingapi.model.Transaction;
import com.gregory.bankingapi.repository.BankAccountRepository;
import com.gregory.bankingapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankAccountService {

    private final BankAccountRepository repository;
    private final TransactionRepository transactionRepository;

    public BankAccountService(
            BankAccountRepository repository,
            TransactionRepository transactionRepository) {

        this.repository = repository;
        this.transactionRepository = transactionRepository;
    }

    public BankAccount createAccount(BankAccount account) {
        return repository.save(account);
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    public BankAccount getAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Bank account not found"));
    }

    public BankAccount deposit(Long id, BigDecimal amount) {

        BankAccount account = getAccountById(id);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be greater than zero");
        }

        account.setBalance(
                account.getBalance().add(amount));

        BankAccount savedAccount = repository.save(account);

        Transaction transaction = new Transaction(
                "DEPOSIT",
                amount,
                null,
                id);

        transactionRepository.save(transaction);

        return savedAccount;
    }

    public BankAccount withdraw(Long id, BigDecimal amount) {

        BankAccount account = getAccountById(id);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal amount must be greater than zero");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient funds");
        }

        account.setBalance(
                account.getBalance().subtract(amount));

        BankAccount savedAccount = repository.save(account);

        Transaction transaction = new Transaction(
                "WITHDRAWAL",
                amount,
                id,
                null);

        transactionRepository.save(transaction);

        return savedAccount;
    }

    public void transfer(
            Long fromAccountId,
            Long toAccountId,
            BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Transfer amount must be greater than zero");
        }

        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException(
                    "Cannot transfer money to the same account");
        }

        BankAccount fromAccount =
                getAccountById(fromAccountId);

        BankAccount toAccount =
                getAccountById(toAccountId);

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient funds");
        }

        fromAccount.setBalance(
                fromAccount.getBalance().subtract(amount));

        toAccount.setBalance(
                toAccount.getBalance().add(amount));

        repository.save(fromAccount);
        repository.save(toAccount);

        Transaction transaction = new Transaction(
                "TRANSFER",
                amount,
                fromAccountId,
                toAccountId);

        transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}