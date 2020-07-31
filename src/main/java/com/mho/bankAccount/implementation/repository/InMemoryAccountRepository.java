package com.mho.bankAccount.implementation.repository;

import com.mho.bankAccount.domain.model.Account;
import com.mho.bankAccount.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    Map<Integer, Account> accounts = new HashMap<>();

    public void save(Account account) {
        accounts.put(account.getId(), account);
    }

    public Account getById(Integer id) {
        return accounts.get(id);
    }
}
