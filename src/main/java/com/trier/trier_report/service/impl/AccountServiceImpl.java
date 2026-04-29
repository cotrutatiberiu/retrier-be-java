package com.trier.trier_report.service.impl;

import com.trier.trier_report.dao.AccountRepository;
import com.trier.trier_report.dao.UserRepository;
import com.trier.trier_report.dto.AccountArchiveRequest;
import com.trier.trier_report.dto.AccountResponse;
import com.trier.trier_report.dto.AccountUpdateRequest;
import com.trier.trier_report.dto.AccountCreateRequest;
import com.trier.trier_report.entity.Account;
import com.trier.trier_report.mapper.AccountMapper;
import com.trier.trier_report.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Transactional
    public void create(AccountCreateRequest payload) {
        Account account = AccountMapper.toEntity(payload);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountResponse update(AccountUpdateRequest payload) {
        Account account = accountRepository.findById(payload.id()).orElseThrow(() -> new EntityNotFoundException("Account not found with ID " + payload.id()));

        if (payload.currencyId() != null) {
            account.setCurrencyId(payload.currencyId());
        }

        if (payload.name() != null && !payload.name().equals(account.getName())) {
            account.setName(payload.name());
        }

        return AccountMapper.toDto(account);
    }

    @Override
    @Transactional
    public void archive(Long id, AccountArchiveRequest payload) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with ID " + id));

        account.setArchived(payload.archived());
        accountRepository.save(account);
    }
}
