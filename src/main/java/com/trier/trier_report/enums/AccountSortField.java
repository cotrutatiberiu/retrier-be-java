package com.trier.trier_report.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AccountSortField {
    NAME("name");

    private final String field;

    AccountSortField(String field) {
        this.field = field;
    }

    @JsonCreator
    public static AccountSortField fromString(String value) {
        return AccountSortField.valueOf(value.toUpperCase());
    }

    public String getField() { return field; }
}