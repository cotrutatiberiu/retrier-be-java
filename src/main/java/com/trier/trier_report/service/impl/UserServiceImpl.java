package com.trier.trier_report.service.impl;

import com.trier.trier_report.dao.AccountRepository;
import com.trier.trier_report.dao.UserRepository;
import com.trier.trier_report.dto.AccountResponseDTO;
import com.trier.trier_report.dto.UserAccountsSearchRequestDTO;
import com.trier.trier_report.entity.Account;
import com.trier.trier_report.mapper.AccountMapper;
import com.trier.trier_report.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<AccountResponseDTO> findAccountsByUserId(Long userId, UserAccountsSearchRequestDTO request) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found: " + userId);
        }

        List<Account> accounts = accountRepository.findAllByUserId(userId);
        long count = accounts.size();
        Pageable pageRequest = PageRequest.of(request.pageNumber(), request.pageSize(), Sort.by(request.sortBy().getField()).ascending());
        var pages = new PageImpl<>(accounts, pageRequest, count);
        return AccountMapper.toDto(pages);
    }

    @Override
    public AccountResponseDTO findAccountByUserId(Long userId, Long accountId) {
        Account account = accountRepository.findByIdAndUserId(userId, accountId).orElseThrow(() -> new EntityNotFoundException("Account not found: " + accountId));

        return AccountMapper.toDto(account);
    }
}
