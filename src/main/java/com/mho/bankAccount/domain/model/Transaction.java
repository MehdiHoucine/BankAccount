package com.mho.bankAccount.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    Integer id;
    Integer fromId;
    Integer toId;
    Integer amount;
}
