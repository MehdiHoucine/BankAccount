package com.mho.bankAccount.usecases;

import com.mho.bankAccount.domain.exception.InsufficientBalanceException;
import com.mho.bankAccount.domain.model.Account;
import com.mho.bankAccount.domain.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateAmountUseCase {

    private AccountRepository accountRepository;

    public UpdateAmountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void hande(Integer accountId, Integer amount) throws InsufficientBalanceException {
        Account account = accountRepository.getById(accountId);

        if (amount < 0 && account.getAmount() + amount < 0) {
            throw new InsufficientBalanceException();
        }

        account.setAmount(account.getAmount() + amount);
        accountRepository.save(account);
    }
}
