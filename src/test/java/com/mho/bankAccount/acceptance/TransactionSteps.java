package com.mho.bankAccount.acceptance;

import com.mho.bankAccount.domain.model.Account;
import com.mho.bankAccount.domain.model.Transaction;
import com.mho.bankAccount.domain.repository.AccountRepository;
import com.mho.bankAccount.domain.repository.TransactionRepository;
import com.mho.bankAccount.usecases.FindTransactionsUseCase;
import io.cucumber.java8.En;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionSteps implements En {
    public TransactionSteps(AccountRepository accountRepository,
                            TransactionRepository transactionRepository) {

        FindTransactionsUseCase findTransactionsUseCase = new FindTransactionsUseCase(transactionRepository);
        List<Transaction> transactions = new ArrayList<>();

        Given("An Account with an id {string}", (String accountId) -> {
            Account account = new Account(Integer.valueOf(accountId), 0);
            accountRepository.save(account);
        });
        Given("A Transaction {string} from {string} to {string}", (String transactionId, String from, String to) -> {
            Transaction transaction = new Transaction(Integer.valueOf(transactionId), Integer.valueOf(from), Integer.valueOf(to), 10);
            transactionRepository.save(transaction);
        });

        When("the User retrieves the transactions for the account {string}", (String accountId) -> {
            transactions.addAll(findTransactionsUseCase.handle(Integer.valueOf(accountId)));
        });
        Then("the Transaction ids are {string}", (String stringIds) -> {
            if (stringIds.equals("")) {
                Assertions.assertThat(transactions).hasSize(0);
            } else {
                List<Integer> ids = Arrays.stream(stringIds.split(",")).map(Integer::valueOf).collect(Collectors.toList());
                Assertions.assertThat(transactions)
                        .extracting(Transaction::getId)
                        .containsExactlyInAnyOrder(ids.toArray(Integer[]::new));
            }
        });
    }

}
