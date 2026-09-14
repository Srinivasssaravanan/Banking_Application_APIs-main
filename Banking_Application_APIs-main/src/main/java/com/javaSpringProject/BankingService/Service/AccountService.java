package com.javaSpringProject.BankingService.Service;

import com.javaSpringProject.BankingService.Entity.Account;
import com.javaSpringProject.BankingService.Entity.User;

import java.util.List;

public interface AccountService {

    // Main registration
    User createAccount(User user);

    // Account operations
    User getUserProfile(Long userId);

    Account getAccountById(Long id);

    String getAccountType(Long id);

    Account addAccountById(Long id, Account account);

    Account deposit(Long id, double amount);

    Account withdraw(Long id, double amount);

    void transferMoney(
            Long sourceId,
            Long targetId,
            double amount
    );

    List<Account> getAllAccounts();

    List<Account> getAccountsByUserId(Long userId);

    void deleteAccount(Long id);

    List<User> getAllUsersWithDetails();
}