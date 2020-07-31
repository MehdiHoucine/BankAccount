package com.mho.bankAccount.rest;

import com.mho.bankAccount.domain.exception.InsufficientBalanceException;
import com.mho.bankAccount.usecases.UpdateAmountUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
public class AccountRest {

    private UpdateAmountUseCase updateAmountUseCase;

    public AccountRest(UpdateAmountUseCase updateAmountUseCase) {
        this.updateAmountUseCase = updateAmountUseCase;
    }

    @PutMapping("{id}")
    public void putAccount(@RequestBody Integer amount, @PathVariable("id") Integer accountId) throws InsufficientBalanceException {
        updateAmountUseCase.hande(accountId, amount);
    }
}
