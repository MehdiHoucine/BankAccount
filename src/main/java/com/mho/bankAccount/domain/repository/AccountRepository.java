package com.mho.bankAccount.domain.repository;

import com.mho.bankAccount.domain.model.Account;

public interface AccountRepository {

    void save(Account account);

    Account getById(Integer id);
}
