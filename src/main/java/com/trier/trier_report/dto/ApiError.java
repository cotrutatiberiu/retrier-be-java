package com.trier.trier_report.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Can be swapped with built-in ProblemDetail
public class ApiError {
    private final int status;
    private final String path;
    private final ArrayList<String> faults;
    private final ArrayList<String> errors;
    LocalDateTime timestamp;

    public ApiError(int status, String errorMessage, String path) {
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.faults = new ArrayList<>();
        this.errors = new ArrayList<String>(List.of(errorMessage));
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ArrayList<String> getFaults() {
        return faults;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void addFault(String fault) {
        this.faults.add(fault);
    }

    public void addError(String error) {
        this.errors.add(error);
    }
}

