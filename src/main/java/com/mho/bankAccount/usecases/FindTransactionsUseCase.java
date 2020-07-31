package com.mho.bankAccount.usecases;

import com.mho.bankAccount.domain.model.Transaction;
import com.mho.bankAccount.domain.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindTransactionsUseCase {

    private TransactionRepository transactionRepository;

    public FindTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> handle(Integer accountId) {
        return transactionRepository.findAllByAccountId(accountId);
    }
}
