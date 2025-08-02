package com.trier.trier_report.service;

import com.trier.trier_report.dto.UserLoginRequest;
import com.trier.trier_report.dto.UserRegisterRequest;
import com.trier.trier_report.dto.UserResponse;
import com.trier.trier_report.entity.User;

import java.util.Optional;

public interface UserService {

    UserResponse register(UserRegisterRequest user);

    User login(UserLoginRequest user);
}
