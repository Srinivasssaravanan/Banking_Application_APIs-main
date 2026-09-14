package com.javaSpringProject.BankingService.Service.implementation;

import com.javaSpringProject.BankingService.Entity.Account;
import com.javaSpringProject.BankingService.Entity.AccountType;
import com.javaSpringProject.BankingService.Entity.User;
import com.javaSpringProject.BankingService.Exception.AccountException;
import com.javaSpringProject.BankingService.Repository.AccountRepository;
import com.javaSpringProject.BankingService.Repository.UserRepository;
import com.javaSpringProject.BankingService.Service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log =
            LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            UserRepository userRepository) {

        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User createAccount(User user) {

        log.info("Creating new user");

        User savedUser = userRepository.save(user);

        log.info("User created successfully with id={}",
                savedUser.getId());

        return savedUser;
    }

    @Override
    public User getUserProfile(Long userId) {

        log.info("Fetching user profile for id={}", userId);

        return userRepository.findById(userId)
                .orElseThrow(() -> {

                    log.error("User not found for id={}", userId);

                    return new AccountException("User not Found");
                });
    }

    @Override
    public Account getAccountById(Long id) {

        log.info("Fetching account for id={}", id);

        return accountRepository.findById(id)
                .orElseThrow(() -> {

                    log.error("Account not found for id={}", id);

                    return new AccountException(
                            "Account does not exist");
                });
    }

    @Override
    public String getAccountType(Long id) {

        log.info("Fetching account type for id={}", id);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {

                    log.error("Account not found for id={}", id);

                    return new AccountException(
                            "Account not Found");
                });

        return account.getAccountType().name();
    }

    @Transactional
    @Override
    public Account addAccountById(Long userId, Account account) {

        log.info("Adding new account for userId={}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {

                    log.error("User not found for id={}", userId);

                    return new AccountException(
                            "User not found");
                });

        account.setUser(user);

        Account savedAccount =
                accountRepository.save(account);

        log.info(
                "Account created with id={} for userId={}",
                savedAccount.getId(),
                userId
        );

        return savedAccount;
    }

    @Transactional
    @Override
    public Account deposit(Long id, double amount) {

        if (amount <= 0) {
            throw new AccountException(
                    "Deposit amount must be greater than zero");
        }

        Account account = accountRepository
                .findByIdWithLock(id)
                .orElseThrow(() ->
                        new AccountException(
                                "Account does not exist"));

        account.setBalance(
                account.getBalance() + amount);

        return accountRepository.save(account);
    }

    @Transactional
    @Override
    public Account withdraw(Long id, double amount) {

        if (amount <= 0) {
            throw new AccountException(
                    "Withdrawal amount must be greater than zero");
        }

        Account account = accountRepository
                .findByIdWithLock(id)
                .orElseThrow(() ->
                        new AccountException(
                                "Account does not exist"));

        if (account.getAccountType() == AccountType.SAVINGS) {

            if (account.getBalance() < amount) {
                throw new AccountException(
                        "Insufficient Balance");
            }

        } else {

            if ((account.getBalance()
                    + account.getFunds()) < amount) {

                throw new AccountException(
                        "Insufficient Funds");
            }
        }

        account.setBalance(
                account.getBalance() - amount);

        return accountRepository.save(account);
    }

    @Transactional
    @Override
    public void transferMoney(
            Long sourceId,
            Long targetId,
            double amount) {

        if (sourceId.equals(targetId)) {
            throw new AccountException(
                    "Cannot transfer to the same account");
        }

        if (amount <= 0) {
            throw new AccountException(
                    "Transfer amount must be greater than zero");
        }

        Long firstId = Math.min(sourceId, targetId);
        Long secondId = Math.max(sourceId, targetId);

        Account firstAccount =
                accountRepository.findByIdWithLock(firstId)
                        .orElseThrow(() ->
                                new AccountException(
                                        "Account " +
                                                firstId +
                                                " not found"));

        Account secondAccount =
                accountRepository.findByIdWithLock(secondId)
                        .orElseThrow(() ->
                                new AccountException(
                                        "Account " +
                                                secondId +
                                                " not found"));

        Account source =
                sourceId.equals(firstId)
                        ? firstAccount
                        : secondAccount;

        Account target =
                targetId.equals(firstId)
                        ? firstAccount
                        : secondAccount;

        if (source.getAccountType() == AccountType.SAVINGS) {

            if (source.getBalance() < amount) {
                throw new AccountException(
                        "Insufficient Balance to transfer");
            }

        } else {

            if ((source.getBalance()
                    + source.getFunds()) < amount) {

                throw new AccountException(
                        "Insufficient Balance and funds");
            }
        }

        source.setBalance(
                source.getBalance() - amount);

        target.setBalance(
                target.getBalance() + amount);

        accountRepository.save(source);
        accountRepository.save(target);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new AccountException(
                                "User not Found"));

        return user.getAccounts();
    }

    @Transactional
    @Override
    public void deleteAccount(Long id) {

        Account account =
                accountRepository.findById(id)
                        .orElseThrow(() ->
                                new AccountException(
                                        "Account does not exist"));

        User user = account.getUser();

        if (user.getAccounts().size() <= 1) {
            userRepository.delete(user);
        } else {
            accountRepository.delete(account);
        }
    }

    @Override
    public List<User> getAllUsersWithDetails() {
        return userRepository.findAll();
    }
}