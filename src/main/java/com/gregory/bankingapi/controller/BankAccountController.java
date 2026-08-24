package com.gregory.bankingapi.controller;

import com.gregory.bankingapi.model.BankAccount;
import com.gregory.bankingapi.model.Transaction;
import com.gregory.bankingapi.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountService service;

    public BankAccountController(BankAccountService service) {
        this.service = service;
    }

    @PostMapping
    public BankAccount createAccount(@RequestBody BankAccount account) {
        return service.createAccount(account);
    }

    @GetMapping
    public List<BankAccount> getAllAccounts() {
        return service.getAllAccounts();
    }

    @GetMapping("/{id}")
    public BankAccount getAccountById(@PathVariable Long id) {
        return service.getAccountById(id);
    }

    @PostMapping("/{id}/deposit")
    public BankAccount deposit(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {

        return service.deposit(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    public BankAccount withdraw(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {

        return service.withdraw(id, amount);
    }

    @PostMapping("/transfer")
    public String transfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam BigDecimal amount) {

        service.transfer(fromAccountId, toAccountId, amount);

        return "Transfer successful";
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }
}