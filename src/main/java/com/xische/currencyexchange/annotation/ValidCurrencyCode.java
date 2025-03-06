package com.xische.currencyexchange.annotation;
import com.xische.currencyexchange.validator.CurrencyCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyCodeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrencyCode {
    String message() default "Invalid currency code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}