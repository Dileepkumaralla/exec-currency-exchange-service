package com.xische.currencyexchange.service;

import com.xische.currencyexchange.model.BillDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final DiscountService discountService;
    private final CurrencyConversionService currencyConversionService;

    @Autowired
    public CurrencyExchangeServiceImpl(DiscountService discountService, CurrencyConversionService currencyConversionService) {
        this.discountService = discountService;
        this.currencyConversionService = currencyConversionService;
    }

    @Override
    public Mono<Double> calculatePayableAmount(BillDetails billDetails) {
        double discountedAmount = discountService.applyDiscounts(billDetails);
        return currencyConversionService.convertCurrency(billDetails, discountedAmount);
    }
}