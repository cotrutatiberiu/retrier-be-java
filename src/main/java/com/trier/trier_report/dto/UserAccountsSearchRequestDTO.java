package com.trier.trier_report.dto;

import com.trier.trier_report.enums.AccountSortField;
import jakarta.validation.constraints.Min;

public record UserAccountsSearchRequestDTO(
        @Min(20)
        int pageSize,
        @Min(0)
        int pageNumber,
        AccountSortField sortBy
)
{
}
