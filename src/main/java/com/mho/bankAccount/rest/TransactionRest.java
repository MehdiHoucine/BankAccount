package com.mho.bankAccount.rest;

import com.mho.bankAccount.domain.exception.InsufficientBalanceException;
import com.mho.bankAccount.domain.exception.InvalidAmountException;
import com.mho.bankAccount.domain.model.Transaction;
import com.mho.bankAccount.rest.dto.TransactionDto;
import com.mho.bankAccount.usecases.FindTransactionsUseCase;
import com.mho.bankAccount.usecases.TransferUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionRest {

    private TransferUseCase transferUseCase;
    private FindTransactionsUseCase findTransactionsUseCase;

    public TransactionRest(TransferUseCase transferUseCase,
                           FindTransactionsUseCase findTransactionsUseCase) {
        this.transferUseCase = transferUseCase;
        this.findTransactionsUseCase = findTransactionsUseCase;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody TransactionDto transactionDto) throws InsufficientBalanceException, InvalidAmountException {
        transferUseCase.handle(transactionDto.getFromId(), transactionDto.getToId(), transactionDto.getAmount());
    }

    @GetMapping
    public List<Transaction> get(@RequestParam(name = "accountId") Integer accountId) {
        return findTransactionsUseCase.handle(accountId);
    }
}

