package com.trier.trier_report.controller;

import com.trier.trier_report.dto.AccountResponse;
import com.trier.trier_report.dto.PaginatedResponse;
import com.trier.trier_report.dto.UserAccountsSearchRequest;
import com.trier.trier_report.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<PaginatedResponse<AccountResponse>> getUserAccounts(@PathVariable Long userId, @Valid @RequestBody UserAccountsSearchRequest request) {
        return ResponseEntity.ok(userService.findAccountsByUserId(userId, request));
    }

    @GetMapping("/{userId}/accounts/{accountId}")
    public ResponseEntity<AccountResponse> getUserAccount(@PathVariable Long userId, @PathVariable Long accountId) {
        return ResponseEntity.ok(userService.findAccountByUserId(userId, accountId));
    }
}
