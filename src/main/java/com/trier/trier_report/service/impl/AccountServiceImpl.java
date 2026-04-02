package com.trier.trier_report.service.impl;

import com.trier.trier_report.dao.AccountRepository;
import com.trier.trier_report.dao.UserRepository;
import com.trier.trier_report.dto.AccountResponse;
import com.trier.trier_report.dto.CreateAccountRequest;
import com.trier.trier_report.entity.Account;
import com.trier.trier_report.entity.User;
import com.trier.trier_report.mapper.AccountMapper;
import com.trier.trier_report.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createAccount(CreateAccountRequest payload) {
        Account account = AccountMapper.toEntity(payload);
        accountRepository.save(account);
    }

    @Override
    public List<AccountResponse> getUserAccounts(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }

        List<Account> userAccounts = accountRepository.findAllByUserId(userId);
        return userAccounts.stream().map(
                AccountMapper::toDto
        ).toList();
    }
}
