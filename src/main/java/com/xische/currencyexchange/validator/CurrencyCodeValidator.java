package com.xische.currencyexchange.validator;


import com.xische.currencyexchange.annotation.ValidCurrencyCode;
import com.xische.currencyexchange.enums.CurrencyCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrencyCodeValidator implements ConstraintValidator<ValidCurrencyCode, CurrencyCode> {

    @Override
    public boolean isValid(CurrencyCode value, ConstraintValidatorContext context) {
        return value != null;
    }
}
