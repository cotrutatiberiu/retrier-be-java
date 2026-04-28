package com.trier.trier_report.controller;

import com.trier.trier_report.dto.AccountArchiveDTO;
import com.trier.trier_report.dto.AccountResponseDTO;
import com.trier.trier_report.dto.AccountUpdateRequestDTO;
import com.trier.trier_report.dto.AccountCreateRequestDTO;
import com.trier.trier_report.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody AccountCreateRequestDTO request) {
        accountService.create(request);

        return ResponseEntity.status(201).build();
    }

    @PatchMapping
    public ResponseEntity<AccountResponseDTO> update(@Valid @RequestBody AccountUpdateRequestDTO request) {
        return ResponseEntity.ok(accountService.update(request));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<Void> archive(@PathVariable Long id, @Valid @RequestBody AccountArchiveDTO request) {
        accountService.archive(id, request);
        return ResponseEntity.status(200).build();
    }
}
