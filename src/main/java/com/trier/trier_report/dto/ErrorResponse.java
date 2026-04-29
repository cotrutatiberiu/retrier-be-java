package com.trier.trier_report.dto;

public class ErrorResponse {
    private int status;
    private String[] faults;
    private Object[] errors;

    public ErrorResponse(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String[] getFaults() {
        return faults;
    }

    public void setFaults(String[] faults) {
        this.faults = faults;
    }

    public Object[] getErrors() {
        return errors;
    }

    public void setErrors(Object[] errors) {
        this.errors = errors;
    }
}
