package com.trier.trier_report.service;

import com.trier.trier_report.dto.CreateAccountRequest;
import com.trier.trier_report.entity.Account;

public interface AccountService {
    void create(CreateAccountRequest account);
}
