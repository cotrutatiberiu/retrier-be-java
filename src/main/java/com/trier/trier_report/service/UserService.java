package com.trier.trier_report.service;

import com.trier.trier_report.dto.AccountResponse;
import com.trier.trier_report.dto.PaginatedResponse;
import com.trier.trier_report.dto.UserAccountsSearchRequest;

public interface UserService {
    AccountResponse findAccountByUserId(Long userId, Long accountId);

    PaginatedResponse<AccountResponse> findAccountsByUserId(Long userId, UserAccountsSearchRequest request);
}
