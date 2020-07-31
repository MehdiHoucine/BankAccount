package com.mho.bankAccount.acceptance;

import com.mho.bankAccount.domain.model.Account;
import com.mho.bankAccount.domain.model.Transaction;
import com.mho.bankAccount.domain.repository.AccountRepository;
import com.mho.bankAccount.domain.repository.TransactionRepository;
import com.mho.bankAccount.usecases.TransferUseCase;
import com.mho.bankAccount.usecases.UpdateAmountUseCase;
import io.cucumber.java8.En;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class AccountSteps implements En {

    public AccountSteps(AccountRepository accountRepository,
                        TransactionRepository transactionRepository) {
        UpdateAmountUseCase updateAmountUseCase = new UpdateAmountUseCase(accountRepository);
        TransferUseCase transferUseCase = new TransferUseCase(accountRepository, transactionRepository);

        List<String> exceptions = new ArrayList<>();

        Given("An Account with an amount {string} and an id {string}", (String amount, String id) -> {
            Account account = new Account(Integer.valueOf(id), Integer.valueOf(amount));
            accountRepository.save(account);
        });

        When("the User withdraws or deposits the amount {string} from the account with the id {string}", (String amount, String id) -> {
            try {
                updateAmountUseCase.hande(Integer.valueOf(id), Integer.valueOf(amount));
            } catch (Exception e) {
                exceptions.add(e.getClass().getName());
            }
        });

        Then("the remaining amount in the account {string} is {string}", (String id, String remaining) -> {
            Account account = accountRepository.getById(Integer.valueOf(id));
            Assertions.assertThat(account.getAmount()).isEqualTo(Integer.valueOf(remaining));
        });
        And("an exception of type {string} is logged", (String exception) -> {
            if (exception.equals("")) {
                Assertions.assertThat(exceptions).isEmpty();
            } else {
                Assertions.assertThat(exceptions).isNotEmpty();
                Assertions.assertThat(exceptions.get(0)).contains(exception);
            }
        });
        When("the User transfers the amount {string} from the account {string} to the account {string}", (String amount, String id1, String id2) -> {
            try {
                transferUseCase.handle(Integer.valueOf(id1), Integer.valueOf(id2), Integer.valueOf(amount));
            } catch (Exception e) {
                exceptions.add(e.getClass().getName());
            }
        });
        And("a Transaction from {string} to {string} is {string} with the amount {string}", (String from, String to, String result, String amount) -> {
            if (result.equals("created")) {
                List<Transaction> transactions = transactionRepository.findAll();
                Assertions.assertThat(transactions).isNotEmpty();
                Assertions.assertThat(transactions).hasSize(1);
                Transaction transaction = transactions.get(0);
                Assertions.assertThat(transaction.getFromId()).isEqualTo(Integer.valueOf(from));
                Assertions.assertThat(transaction.getToId()).isEqualTo(Integer.valueOf(to));
                Assertions.assertThat(transaction.getAmount()).isEqualTo(Integer.valueOf(amount));
            } else if (result.equals("not created")) {
                Assertions.assertThat(transactionRepository.findAll()).isEmpty();
            }
        });
    }
}
