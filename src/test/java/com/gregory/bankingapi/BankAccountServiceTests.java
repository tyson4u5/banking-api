package com.gregory.bankingapi;

import com.gregory.bankingapi.model.BankAccount;
import com.gregory.bankingapi.repository.BankAccountRepository;
import com.gregory.bankingapi.repository.TransactionRepository;
import com.gregory.bankingapi.service.BankAccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountServiceTests {

    private BankAccountRepository bankAccountRepository;
    private TransactionRepository transactionRepository;
    private BankAccountService service;

    @BeforeEach
    void setUp() {
        bankAccountRepository = Mockito.mock(BankAccountRepository.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);

        service = new BankAccountService(
                bankAccountRepository,
                transactionRepository
        );
    }

    @Test
    void depositShouldIncreaseBalance() {

        BankAccount account = new BankAccount();
        account.setBalance(new BigDecimal("1000.00"));

        when(bankAccountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(bankAccountRepository.save(account))
                .thenReturn(account);

        BankAccount result =
                service.deposit(1L, new BigDecimal("200.00"));

        assertEquals(
                new BigDecimal("1200.00"),
                result.getBalance()
        );

        verify(bankAccountRepository).save(account);
        verify(transactionRepository).save(any());
    }

    @Test
    void withdrawShouldDecreaseBalance() {

        BankAccount account = new BankAccount();
        account.setBalance(new BigDecimal("1000.00"));

        when(bankAccountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(bankAccountRepository.save(account))
                .thenReturn(account);

        BankAccount result =
                service.withdraw(1L, new BigDecimal("300.00"));

        assertEquals(
                new BigDecimal("700.00"),
                result.getBalance()
        );

        verify(bankAccountRepository).save(account);
        verify(transactionRepository).save(any());
    }

    @Test
    void withdrawShouldRejectInsufficientFunds() {

        BankAccount account = new BankAccount();
        account.setBalance(new BigDecimal("500.00"));

        when(bankAccountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.withdraw(
                                1L,
                                new BigDecimal("1000.00")
                        )
                );

        assertEquals(
                "Insufficient funds",
                exception.getMessage()
        );

        verify(bankAccountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void transferShouldMoveMoneyBetweenAccounts() {

        BankAccount from = new BankAccount();
        from.setBalance(new BigDecimal("1000.00"));

        BankAccount to = new BankAccount();
        to.setBalance(new BigDecimal("500.00"));

        when(bankAccountRepository.findById(1L))
                .thenReturn(Optional.of(from));

        when(bankAccountRepository.findById(2L))
                .thenReturn(Optional.of(to));

        service.transfer(
                1L,
                2L,
                new BigDecimal("300.00")
        );

        assertEquals(
                new BigDecimal("700.00"),
                from.getBalance()
        );

        assertEquals(
                new BigDecimal("800.00"),
                to.getBalance()
        );

        verify(bankAccountRepository).save(from);
        verify(bankAccountRepository).save(to);
        verify(transactionRepository).save(any());
    }
}
