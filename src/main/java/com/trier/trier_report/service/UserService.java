package com.trier.trier_report.service;

import com.trier.trier_report.dto.UserLoginRequest;
import com.trier.trier_report.dto.UserRegisterRequest;
import com.trier.trier_report.dto.UserResponse;
import com.trier.trier_report.entity.User;
import com.trier.trier_report.util.LoginResult;

public interface UserService {

    UserResponse register(UserRegisterRequest user);

    LoginResult authenticateAndGenerateTokens(UserLoginRequest user);
}
