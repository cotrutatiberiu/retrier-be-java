package com.trier.trier_report.mapper;

import com.trier.trier_report.dto.AccountResponse;
import com.trier.trier_report.dto.AccountCreateRequest;
import com.trier.trier_report.entity.Account;

public class AccountMapper {
    public static Account toEntity(AccountCreateRequest payload) {
        return new Account(payload.userId(), payload.accountTypeId(), payload.currencyId(), payload.name());
    }

    public static AccountResponse toDto(Account account) {
        return new AccountResponse(account.getId(), account.getUserId(), account.getCurrencyId(), account.getName(), account.isArchived(), account.getCreatedAt(), account.getUpdatedAt());
    }
}
