package com.mho.bankAccount.domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Account {
    private Integer id;
    private Integer amount;
}
