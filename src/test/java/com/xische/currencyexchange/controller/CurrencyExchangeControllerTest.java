package com.xische.currencyexchange.controller;

import com.xische.currencyexchange.TestSecurityConfig;
import com.xische.currencyexchange.enums.CurrencyCode;
import com.xische.currencyexchange.model.BillDetails;
import com.xische.currencyexchange.model.Item;
import com.xische.currencyexchange.service.CurrencyExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(CurrencyExchangeController.class)
@Import(TestSecurityConfig.class)
public class CurrencyExchangeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculatePayableAmount() {;
        double totalAmount = 1001.0;
        boolean isEmployee = true;
        boolean isAffiliate = false;
        int customerTenure = 5;
        CurrencyCode originalCurrency = CurrencyCode.USD;
        CurrencyCode targetCurrency = CurrencyCode.EUR;

        BillDetails billDetails = new BillDetails(
                List.of(
                        new Item("Apple", 1.0, true),
                        new Item("Laptop", 1000.0, false)), totalAmount, isEmployee, isAffiliate, customerTenure, originalCurrency, targetCurrency
        );

        when(currencyExchangeService.calculatePayableAmount(any(BillDetails.class)))
                .thenReturn(Mono.just(85000.0));

        webTestClient.post()
                .uri("/api/currency-exchange/calculate")
                .bodyValue(billDetails)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class)
                .isEqualTo(85000.0);
    }

    @Test
    public void testCalculatePayableAmountError() {
        List<Item> items = List.of(
                new Item("Apple", 1.0, true),
                new Item("Laptop", 1000.0, false)
        );
        double totalAmount = 1001.0;
        boolean isEmployee = true;
        boolean isAffiliate = false;
        int customerTenure = 5;
        CurrencyCode originalCurrency = CurrencyCode.USD;
        CurrencyCode targetCurrency = CurrencyCode.EUR;

        BillDetails billDetails = new BillDetails(
                items, totalAmount, isEmployee, isAffiliate, customerTenure, originalCurrency, targetCurrency
        );

        when(currencyExchangeService.calculatePayableAmount(any(BillDetails.class)))
                .thenReturn(Mono.error(new RuntimeException("Error calculating payable amount")));

        webTestClient.post()
                .uri("/api/currency-exchange/calculate")
                .bodyValue(billDetails)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
