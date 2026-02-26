package com.trier.trier_report.service;

import com.trier.trier_report.dto.UserLoginRequest;
import com.trier.trier_report.dto.UserRegisterRequest;
import com.trier.trier_report.dto.UserResponse;

public interface UserService {
    UserResponse register(UserRegisterRequest request);

    String login(UserLoginRequest userLoginRequest);

    String isAuthenticated();
}
