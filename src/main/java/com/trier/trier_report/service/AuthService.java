package com.trier.trier_report.service;

import com.trier.trier_report.dto.UserLoginRequestDTO;
import com.trier.trier_report.dto.UserRegisterRequestDTO;
import com.trier.trier_report.dto.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(UserRegisterRequestDTO request);

    String login(UserLoginRequestDTO userLoginRequestDTO);

    String isAuthenticated();
}
