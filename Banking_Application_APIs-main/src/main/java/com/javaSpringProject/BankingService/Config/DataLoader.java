package com.javaSpringProject.BankingService.Config;

import com.javaSpringProject.BankingService.Entity.Account;
import com.javaSpringProject.BankingService.Entity.AccountType;
import com.javaSpringProject.BankingService.Entity.Contact;
import com.javaSpringProject.BankingService.Entity.User;
import com.javaSpringProject.BankingService.Repository.AccountRepository;
import com.javaSpringProject.BankingService.Repository.ContactRepository;
import com.javaSpringProject.BankingService.Repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            UserRepository userRepository,
            AccountRepository accountRepository,
            ContactRepository contactRepository) {

        return args -> {

            // Clear old data
            accountRepository.deleteAll();
            contactRepository.deleteAll();
            userRepository.deleteAll();

            // =========================
            // User 1
            // =========================

            User user1 = new User();

            user1.setFirstName("Arjun");
            user1.setLastName("Reddy");
            user1.setDateOfBirth(LocalDate.of(1997, 11, 5));
            user1.setGender("MALE");
            user1.setPrefix("Mr.");

            userRepository.save(user1);

            Contact contact1 = new Contact();

            contact1.setEmail("arjun.reddy@example.com");
            contact1.setPhoneNumber1("9012345678");
            contact1.setAddressLine1("Banjara Hills");
            contact1.setCity("Hyderabad");
            contact1.setState("Telangana");
            contact1.setPincode("500034");
            contact1.setUser(user1);

            contactRepository.save(contact1);

            user1.setContact(contact1);

            Account account1 = new Account();

            account1.setAccountType(AccountType.SAVINGS);
            account1.setAccountNumber("ACC1001");
            account1.setBranchIfsc("ICIC0001234");
            account1.setBalance(7000.0);
            account1.setFunds(0.0);
            account1.setUser(user1);

            accountRepository.save(account1);

            user1.getAccounts().add(account1);


            // =========================
            // User 2
            // =========================

            User user2 = new User();

            user2.setFirstName("Sneha");
            user2.setLastName("Kapoor");
            user2.setDateOfBirth(LocalDate.of(1999, 3, 15));
            user2.setGender("FEMALE");
            user2.setPrefix("Ms.");

            userRepository.save(user2);

            Contact contact2 = new Contact();

            contact2.setEmail("sneha.kapoor@example.com");
            contact2.setPhoneNumber1("9123456789");
            contact2.setAddressLine1("Andheri West");
            contact2.setCity("Mumbai");
            contact2.setState("Maharashtra");
            contact2.setPincode("400053");
            contact2.setUser(user2);

            contactRepository.save(contact2);

            user2.setContact(contact2);

            Account account2 = new Account();

            account2.setAccountType(AccountType.CURRENT);
            account2.setAccountNumber("ACC2002");
            account2.setBranchIfsc("HDFC0005678");
            account2.setBalance(3000.0);
            account2.setFunds(5000.0);
            account2.setUser(user2);

            accountRepository.save(account2);

            user2.getAccounts().add(account2);


            // =========================
            // User 3
            // =========================

            User user3 = new User();

            user3.setFirstName("Vikram");
            user3.setLastName("Singh");
            user3.setDateOfBirth(LocalDate.of(1995, 8, 20));
            user3.setGender("MALE");
            user3.setPrefix("Mr.");

            userRepository.save(user3);

            Contact contact3 = new Contact();

            contact3.setEmail("vikram.singh@example.com");
            contact3.setPhoneNumber1("9988776655");
            contact3.setAddressLine1("MG Road");
            contact3.setCity("Bangalore");
            contact3.setState("Karnataka");
            contact3.setPincode("560001");
            contact3.setUser(user3);

            contactRepository.save(contact3);

            user3.setContact(contact3);

            Account account3 = new Account();

            account3.setAccountType(AccountType.SAVINGS);
            account3.setAccountNumber("ACC3003");
            account3.setBranchIfsc("SBI0004321");
            account3.setBalance(10000.0);
            account3.setFunds(0.0);
            account3.setUser(user3);

            accountRepository.save(account3);

            user3.getAccounts().add(account3);


            // =========================
            // Second Account for User 3
            // =========================

            Account account4 = new Account();

            account4.setAccountType(AccountType.CURRENT);
            account4.setAccountNumber("ACC3004");
            account4.setBranchIfsc("SBI0004321");
            account4.setBalance(2000.0);
            account4.setFunds(8000.0);
            account4.setUser(user3);

            accountRepository.save(account4);

            user3.getAccounts().add(account4);

            System.out.println("Demo data loaded successfully!");
        };
    }
}