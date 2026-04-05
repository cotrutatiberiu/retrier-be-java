package com.trier.trier_report.service;

import com.trier.trier_report.dto.AccountArchive;
import com.trier.trier_report.dto.AccountResponse;
import com.trier.trier_report.dto.AccountUpdateRequest;
import com.trier.trier_report.dto.AccountCreateRequest;
import com.trier.trier_report.entity.Account;

import java.util.List;

public interface AccountService {
    void createAccount(AccountCreateRequest payload);
    List<AccountResponse> getUserAccounts(Long userId);
    AccountResponse updateAccount(AccountUpdateRequest payload);
    void archiveAccount(Long id,AccountArchive payload);
}
