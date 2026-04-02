package com.trier.trier_report.service;

import com.trier.trier_report.dto.AccountResponse;
import com.trier.trier_report.dto.CreateAccountRequest;
import com.trier.trier_report.entity.Account;

import java.util.List;

public interface AccountService {
    void createAccount(CreateAccountRequest account);
    List<AccountResponse> getUserAccounts(Long userId);
}
