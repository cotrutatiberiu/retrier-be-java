package com.trier.trier_report.mapper;

import com.trier.trier_report.dto.CreateAccountRequest;
import com.trier.trier_report.entity.Account;

public class AccountMapper {
    public static Account toEntity(CreateAccountRequest payload) {
        return new Account(payload.userId(), payload.accountTypeId(), payload.currencyId(), payload.name());
    }
}
