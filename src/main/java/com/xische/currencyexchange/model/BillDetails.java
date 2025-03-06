package com.xische.currencyexchange.model;

import com.xische.currencyexchange.annotation.ValidCurrencyCode;
import com.xische.currencyexchange.enums.CurrencyCode;

import java.util.List;

public record BillDetails(
        List<Item> items,
        double totalAmount,
        boolean isEmployee,
        boolean isAffiliate,
        int customerTenure,
        @ValidCurrencyCode CurrencyCode originalCurrency,
        @ValidCurrencyCode CurrencyCode targetCurrency
) {
}