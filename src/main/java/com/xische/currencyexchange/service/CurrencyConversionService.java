package com.xische.currencyexchange.service;


import com.xische.currencyexchange.invoker.ExchangeRateApiClient;
import com.xische.currencyexchange.model.BillDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class CurrencyConversionService {

    private final ExchangeRateApiClient exchangeRateApiClient;

    @Autowired
    public CurrencyConversionService(ExchangeRateApiClient exchangeRateApiClient) {
        this.exchangeRateApiClient = exchangeRateApiClient;
    }

    public Mono<Double> convertCurrency(BillDetails billDetails, double amount) {
        return exchangeRateApiClient.getExchangeRates(billDetails.originalCurrency().name())
                .map(exchangeRateApiResponse -> {
                    BigDecimal exchangeRate = exchangeRateApiResponse.getRates().get(billDetails.targetCurrency().name());
                    return amount * exchangeRate.doubleValue();
                });
    }
}