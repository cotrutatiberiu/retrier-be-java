package com.trier.trier_report.controller;

import com.trier.trier_report.dto.AccountResponse;
import com.trier.trier_report.dto.CreateAccountRequest;
import com.trier.trier_report.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getUserAccounts(@RequestParam Long userId) {
        return ResponseEntity.ok(accountService.getUserAccounts(userId));
    }
}
