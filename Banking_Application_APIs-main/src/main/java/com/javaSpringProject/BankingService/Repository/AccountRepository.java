package com.javaSpringProject.BankingService.Repository;

import com.javaSpringProject.BankingService.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
