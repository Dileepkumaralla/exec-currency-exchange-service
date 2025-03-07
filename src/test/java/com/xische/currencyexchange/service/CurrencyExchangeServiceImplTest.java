package com.xische.currencyexchange.service;


import com.xische.currencyexchange.enums.CurrencyCode;
import com.xische.currencyexchange.model.BillDetails;
import com.xische.currencyexchange.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeServiceImplTest {

    @Mock
    private DiscountService discountService;

    @Mock
    private CurrencyConversionService currencyConversionService;

    @InjectMocks
    private CurrencyExchangeServiceImpl currencyExchangeService;

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
    public void testCalculatePayableAmount() {
        when(discountService.applyDiscounts(any(BillDetails.class))).thenReturn(900.0);
        when(currencyConversionService.convertCurrency(any(BillDetails.class), any(Double.class)))
                .thenReturn(Mono.just(765.0));

        Mono<Double> result = currencyExchangeService.calculatePayableAmount(billDetails);

        StepVerifier.create(result)
                .expectNext(765.0)
                .verifyComplete();
    }

    @Test
    public void testCalculatePayableAmountError() {
        when(discountService.applyDiscounts(any(BillDetails.class))).thenReturn(900.0);
        when(currencyConversionService.convertCurrency(any(BillDetails.class), any(Double.class)))
                .thenReturn(Mono.error(new RuntimeException("Error converting currency")));

        Mono<Double> result = currencyExchangeService.calculatePayableAmount(billDetails);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}