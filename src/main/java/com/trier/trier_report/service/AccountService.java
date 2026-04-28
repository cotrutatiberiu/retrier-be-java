package com.trier.trier_report.service;

import com.trier.trier_report.dto.AccountArchiveDTO;
import com.trier.trier_report.dto.AccountResponseDTO;
import com.trier.trier_report.dto.AccountUpdateRequestDTO;
import com.trier.trier_report.dto.AccountCreateRequestDTO;

public interface AccountService {
    void create(AccountCreateRequestDTO payload);
    AccountResponseDTO update(AccountUpdateRequestDTO payload);
    void archive(Long id, AccountArchiveDTO payload);
}
