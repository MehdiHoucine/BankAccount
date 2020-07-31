package com.mho.bankAccount.implementation.repository;

import com.mho.bankAccount.domain.model.Transaction;
import com.mho.bankAccount.domain.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryTransactionHistory implements TransactionRepository {

    List<Transaction> transactions = new ArrayList<>();

    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> findAll() {
        return transactions;
    }

    public List<Transaction> findAllByAccountId(Integer accountId) {
        return transactions.stream().filter(transaction -> transaction.getFromId().equals(accountId) || transaction.getToId().equals(accountId)).collect(Collectors.toList());
    }
}
