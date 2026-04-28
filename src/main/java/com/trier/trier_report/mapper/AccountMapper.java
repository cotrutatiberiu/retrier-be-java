package com.trier.trier_report.mapper;

import com.trier.trier_report.dto.AccountResponseDTO;
import com.trier.trier_report.dto.AccountCreateRequestDTO;
import com.trier.trier_report.entity.Account;
import org.springframework.data.domain.Page;

public class AccountMapper {
    public static Account toEntity(AccountCreateRequestDTO payload) {
        return new Account(payload.userId(), payload.accountTypeId(), payload.currencyId(), payload.name());
    }

    public static AccountResponseDTO toDto(Account account) {
        return new AccountResponseDTO(account.getId(), account.getUserId(), account.getCurrencyId(), account.getName(), account.isArchived(), account.getCreatedAt(), account.getUpdatedAt());
    }

    public static Page<AccountResponseDTO> toDto(Page<Account> pageAccount) {
        return pageAccount.map(AccountMapper::toDto);
    }
}
