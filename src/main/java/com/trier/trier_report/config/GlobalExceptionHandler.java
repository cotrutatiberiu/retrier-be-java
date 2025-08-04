package com.trier.trier_report.config;

import com.trier.trier_report.dto.ErrorResponse;
import com.trier.trier_report.exception.AccessTokenExpiredException;
import com.trier.trier_report.exception.EmailUsedException;
import com.trier.trier_report.exception.RefreshTokenExpiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailUsedException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyRegisteredException(EmailUsedException ex) {
        ErrorResponse response = new ErrorResponse(409);
        response.setErrors(new String[]{ex.getMessage()});

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponse response = new ErrorResponse(401);
        response.setErrors(new String[]{ex.getMessage()});

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(RefreshTokenExpiredException ex) {
        ErrorResponse response = new ErrorResponse(403);
        response.setErrors(new String[]{ex.getMessage()});

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AccessTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(AccessTokenExpiredException ex) {
        ErrorResponse response = new ErrorResponse(401);
        response.setErrors(new String[]{ex.getMessage()});

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
