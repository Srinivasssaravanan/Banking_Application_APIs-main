package com.javaSpringProject.BankingService.Controller;

import com.javaSpringProject.BankingService.Entity.Account;
import com.javaSpringProject.BankingService.Entity.User;
import com.javaSpringProject.BankingService.Service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Register User + Account
    @PostMapping("/register")
    public ResponseEntity<User> createAccount(@RequestBody User user) {

        User savedUser = accountService.createAccount(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Add Account by User ID
    @PostMapping("/user/{userId}/addAccount")
    public ResponseEntity<Account> addAccountById(
            @PathVariable Long userId,
            @RequestBody Account account) {

        Account savedAccount =
                accountService.addAccountById(userId, account);

        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    // Get User Profile
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserProfile(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                accountService.getUserProfile(userId)
        );
    }

    // Transfer Money
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(
            @RequestParam Long sourceId,
            @RequestParam Long targetId,
            @RequestParam double amount) {

        accountService.transferMoney(
                sourceId,
                targetId,
                amount
        );

        return ResponseEntity.ok("Transferred successfully!");
    }

    // Get Account by ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                accountService.getAccountById(id)
        );
    }

    // Get Accounts by User ID
    @GetMapping("/user/{userId}/accounts")
    public ResponseEntity<List<Account>> getAccountsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                accountService.getAccountsByUserId(userId)
        );
    }

    // Deposit Money
    @PutMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(
            @PathVariable Long id,
            @RequestParam double amount) {

        return ResponseEntity.ok(
                accountService.deposit(id, amount)
        );
    }

    // Withdraw Money
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(
            @PathVariable Long id,
            @RequestParam double amount) {

        return ResponseEntity.ok(
                accountService.withdraw(id, amount)
        );
    }

    // Get All Accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {

        return ResponseEntity.ok(
                accountService.getAllAccounts()
        );
    }

    // Delete Account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable Long id) {

        accountService.deleteAccount(id);

        return ResponseEntity.ok(
                "Account deleted successfully!"
        );
    }

    // Get Account Type
    @GetMapping("/{id}/type")
    public ResponseEntity<Map<String, String>> getAccountType(
            @PathVariable Long id) {

        String type = accountService.getAccountType(id);

        return ResponseEntity.ok(
                Map.of("Account type", type)
        );
    }

    // Get All Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsersWithDetails() {

        return ResponseEntity.ok(
                accountService.getAllUsersWithDetails()
        );
    }
}