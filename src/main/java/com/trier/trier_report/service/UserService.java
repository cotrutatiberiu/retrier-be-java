package com.trier.trier_report.service;

import com.trier.trier_report.dto.AccountResponseDTO;
import com.trier.trier_report.dto.UserAccountsSearchRequestDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    AccountResponseDTO findAccountByUserId(Long userId, Long accountId);

    Page<AccountResponseDTO> findAccountsByUserId(Long userId, UserAccountsSearchRequestDTO request);
}
