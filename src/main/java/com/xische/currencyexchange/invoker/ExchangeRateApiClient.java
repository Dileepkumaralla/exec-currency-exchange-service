package com.xische.currencyexchange.invoker;

import com.xische.currencyexchange.config.CacheConfig;
import com.xische.currencyexchange.exception.ExchangeRateApiException;
import com.xische.currencyexchange.model.ExchangeRateApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class ExchangeRateApiClient {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExchangeRateApiClient.class);
    private final WebClient exchangeRateClient;

    public ExchangeRateApiClient(WebClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    @Value("${exchange-api.api-key}")
    private String exchangeRateApiKey;

    @Cacheable(value = {CacheConfig.CURRENCY_LIST_CACHE})
    public Mono<ExchangeRateApiResponse> getExchangeRates(String baseCurrency) {
        log.info("Fetching exchange rates for base currency: {}", baseCurrency);
        return exchangeRateClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{base_currency}")
                        .queryParam("apikey", exchangeRateApiKey)
                        .build(baseCurrency))
                .retrieve()
                .bodyToMono(ExchangeRateApiResponse.class)
                .doOnSuccess(response -> log.info("Successfully fetched exchange rates for base currency: {}", baseCurrency))
                .doOnError(error -> {
                    log.error("Failed to fetch exchange rates for base currency: {}", baseCurrency, error);
                    throw new ExchangeRateApiException("Failed to fetch exchange rates for base currency: " + baseCurrency, error);
                });
    }
}