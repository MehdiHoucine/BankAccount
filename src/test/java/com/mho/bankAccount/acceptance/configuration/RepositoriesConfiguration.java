package com.mho.bankAccount.acceptance.configuration;

import com.mho.bankAccount.domain.repository.AccountRepository;
import com.mho.bankAccount.domain.repository.TransactionRepository;
import com.mho.bankAccount.implementation.repository.InMemoryAccountRepository;
import com.mho.bankAccount.implementation.repository.InMemoryTransactionHistory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@TestConfiguration
public class RepositoriesConfiguration {

    @Bean
    @Scope("cucumber-glue")
    public AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }

    @Bean
    @Scope("cucumber-glue")
    public TransactionRepository transactionRepository() {
        return new InMemoryTransactionHistory();
    }
}
