package com.trier.trier_report.dto;

import com.trier.trier_report.enums.AccountSortField;
import jakarta.validation.constraints.Min;

public record UserAccountsSearchRequest(
        @Min(1)
        int pageSize,
        @Min(0)
        int pageNumber,
        AccountSortField sortBy
)
{
}
