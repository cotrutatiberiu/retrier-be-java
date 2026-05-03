package com.trier.trier_report.service.impl;

import com.trier.trier_report.dao.AccountRepository;
import com.trier.trier_report.dao.UserRepository;
import com.trier.trier_report.dto.UserAccountsSearchRequest;
import com.trier.trier_report.enums.AccountSortField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findAccountsByUserId() {
        Long userId = 1L;
        UserAccountsSearchRequest request = new UserAccountsSearchRequest(0, 10, AccountSortField.NAME);

        when(userRepository.existsById(userId)).thenReturn(true);


        when(accountRepository.findAllByUserId(userId,pageable))
    }

    @Test
    void findAccountByUserId() {
    }
}