package com.mho.bankAccount.rest.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class TransactionDto {
    Integer fromId;
    Integer toId;
    Integer amount;
}
