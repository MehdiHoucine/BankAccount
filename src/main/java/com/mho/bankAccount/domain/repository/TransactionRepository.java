package com.mho.bankAccount.domain.repository;

import com.mho.bankAccount.domain.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);

    List<Transaction> findAll();

    List<Transaction> findAllByAccountId(Integer accountId);
}
