package com.trier.trier_report.config;

import com.trier.trier_report.dto.ErrorResponseDTO;
import com.trier.trier_report.exception.AccessTokenExpiredException;
import com.trier.trier_report.exception.EmailUsedException;
import com.trier.trier_report.exception.RefreshTokenExpiredException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailUsedException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyRegisteredException(EmailUsedException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(409);
        response.setErrors(new String[]{ex.getMessage()});

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(401);
        response.setErrors(new String[]{ex.getMessage()});

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(RefreshTokenExpiredException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(403);
        response.setErrors(new String[]{ex.getMessage()});

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AccessTokenExpiredException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(AccessTokenExpiredException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(401);
        response.setErrors(new String[]{ex.getMessage()});

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(EntityNotFoundException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(404);
        response.setErrors(new String[]{ex.getMessage()});

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
