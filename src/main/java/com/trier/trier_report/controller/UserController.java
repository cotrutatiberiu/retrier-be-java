package com.trier.trier_report.controller;

import com.trier.trier_report.dto.AccountResponseDTO;
import com.trier.trier_report.dto.UserAccountsSearchRequestDTO;
import com.trier.trier_report.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Page<AccountResponseDTO>> getUserAccounts(@PathVariable Long userId, @Valid @RequestBody UserAccountsSearchRequestDTO request) {
        return ResponseEntity.ok(userService.findAccountsByUserId(userId, request));
    }

    @GetMapping("/{userId}/accounts/{accountId}")
    public ResponseEntity<AccountResponseDTO> getUserAccount(@PathVariable Long userId, @PathVariable Long accountId) {
        return ResponseEntity.ok(userService.findAccountByUserId(userId, accountId));
    }
}
