package com.xische.currencyexchange.service;

import com.xische.currencyexchange.enums.CurrencyCode;
import com.xische.currencyexchange.invoker.ExchangeRateApiClient;
import com.xische.currencyexchange.model.BillDetails;
import com.xische.currencyexchange.model.ExchangeRateApiResponse;
import com.xische.currencyexchange.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyConversionServiceTest {

    @Mock
    private ExchangeRateApiClient exchangeRateApiClient;

    @InjectMocks
    private CurrencyConversionService currencyConversionService;

    private BillDetails billDetails;

    @BeforeEach
    public void setUp() {
        billDetails = new BillDetails(
                List.of(
                        new Item("Apple", 1.0, true),
                        new Item("Laptop", 1000.0, false)
                ),
                1001.0,
                true,
                false,
                5,
                CurrencyCode.USD,
                CurrencyCode.EUR
        );
    }

    @Test
    public void testConvertCurrency() {
        ExchangeRateApiResponse exchangeRateApiResponse = new ExchangeRateApiResponse(
                1633024800L,
                "2021-09-30T00:00:00Z",
                1633111200L,
                "2021-10-01T00:00:00Z",
                "USD",
                Map.of("EUR", BigDecimal.valueOf(0.85)),
                null,
                "success"
        );

        when(exchangeRateApiClient.getExchangeRates(anyString()))
                .thenReturn(Mono.just(exchangeRateApiResponse));

        Mono<Double> result = currencyConversionService.convertCurrency(billDetails, 1001.0);

        StepVerifier.create(result)
                .expectNext(850.85)
                .verifyComplete();
    }

    @Test
    public void testConvertCurrencyError() {
        when(exchangeRateApiClient.getExchangeRates(anyString()))
                .thenReturn(Mono.error(new RuntimeException("Error fetching exchange rates")));

        Mono<Double> result = currencyConversionService.convertCurrency(billDetails, 1001.0);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}