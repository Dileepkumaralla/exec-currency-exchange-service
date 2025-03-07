package com.xische.currencyexchange.invoker;

import com.xische.currencyexchange.exception.ExchangeRateApiException;
import com.xische.currencyexchange.model.ExchangeRateApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("all")
public class ExchangeRateApiClientTest {

    @Mock
    private WebClient exchangeRateClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ExchangeRateApiClient exchangeRateApiClient;

    @Value("${exchange-api.api-key}")
    private String exchangeRateApiKey;

    @BeforeEach
    public void setUp() {
        when(exchangeRateClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    public void testGetExchangeRatesSuccess() {
        ExchangeRateApiResponse response = new ExchangeRateApiResponse(
                1633024800L,
                "2021-09-30T00:00:00Z",
                1633111200L,
                "2021-10-01T00:00:00Z",
                "USD",
                Map.of("EUR", BigDecimal.valueOf(0.85)),
                null,
                "success"
        );

        when(responseSpec.bodyToMono(ExchangeRateApiResponse.class)).thenReturn(Mono.just(response));

        Mono<ExchangeRateApiResponse> result = exchangeRateApiClient.getExchangeRates("USD");

        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    public void testGetExchangeRatesError() {
        when(responseSpec.bodyToMono(ExchangeRateApiResponse.class))
                .thenReturn(Mono.error(new WebClientResponseException(500, "Internal Server Error", null, null, null)));

        Mono<ExchangeRateApiResponse> result = exchangeRateApiClient.getExchangeRates("USD");

        StepVerifier.create(result)
                .expectError(ExchangeRateApiException.class)
                .verify();
    }

    @Test
    public void testGetExchangeRatesInvalidCurrency() {
        when(responseSpec.bodyToMono(ExchangeRateApiResponse.class))
                .thenReturn(Mono.error(new WebClientResponseException(400, "Bad Request", null, null, null)));

        Mono<ExchangeRateApiResponse> result = exchangeRateApiClient.getExchangeRates("INVALID");

        StepVerifier.create(result)
                .expectError(ExchangeRateApiException.class)
                .verify();
    }

    @Test
    public void testGetExchangeRatesTimeout() {
        when(responseSpec.bodyToMono(ExchangeRateApiResponse.class))
                .thenReturn(Mono.error(new WebClientResponseException(504, "Gateway Timeout", null, null, null)));

        Mono<ExchangeRateApiResponse> result = exchangeRateApiClient.getExchangeRates("USD");

        StepVerifier.create(result)
                .expectError(ExchangeRateApiException.class)
                .verify();
    }

    @Test
    public void testGetExchangeRatesUnauthorized() {
        when(responseSpec.bodyToMono(ExchangeRateApiResponse.class))
                .thenReturn(Mono.error(new WebClientResponseException(401, "Unauthorized", null, null, null)));

        Mono<ExchangeRateApiResponse> result = exchangeRateApiClient.getExchangeRates("USD");

        StepVerifier.create(result)
                .expectError(ExchangeRateApiException.class)
                .verify();
    }
}