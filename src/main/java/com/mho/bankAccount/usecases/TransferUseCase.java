package com.mho.bankAccount.usecases;

import com.mho.bankAccount.domain.exception.InsufficientBalanceException;
import com.mho.bankAccount.domain.exception.InvalidAmountException;
import com.mho.bankAccount.domain.model.Account;
import com.mho.bankAccount.domain.model.Transaction;
import com.mho.bankAccount.domain.repository.AccountRepository;
import com.mho.bankAccount.domain.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransferUseCase {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public TransferUseCase(AccountRepository accountRepository,
                           TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void handle(Integer accountId1, Integer accountId2, Integer amount) throws InsufficientBalanceException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException();
        }
        Account account1 = accountRepository.getById(accountId1);
        Account account2 = accountRepository.getById(accountId2);

        Integer amount1 = account1.getAmount();
        Integer amount2 = account2.getAmount();

        if (amount1 - amount < 0) {
            throw new InsufficientBalanceException();
        }

        account1.setAmount(amount1 - amount);
        account2.setAmount(amount2 + amount);

        accountRepository.save(account1);
        accountRepository.save(account2);

        // Transaction id generation is out of the scope
        Transaction transaction = new Transaction(1, accountId1, accountId2, amount);
        transactionRepository.save(transaction);
    }
}
