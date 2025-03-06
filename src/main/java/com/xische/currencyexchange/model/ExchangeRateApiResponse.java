package com.xische.currencyexchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateApiResponse(
        @JsonProperty("time_last_update_unix") Long timeLastUpdateUnix,
        @JsonProperty("time_last_update_utc") String timeLastUpdateUtc,
        @JsonProperty("time_next_update_unix") Long timeNextUpdateUnix,
        @JsonProperty("time_next_update_utc") String timeNextUpdateUtc,
        @JsonProperty("base_code") String baseCode,
        Map<String, BigDecimal> rates,
        @JsonProperty("error-type") String error,
        String result
) implements CurrencyApiResponse {

    @Override
    public Map<String, BigDecimal> getRates() {
        return this.rates;
    }

    @Override
    public String getError() {
        return this.error;
    }

    @Override
    public Boolean isSuccess() {
        return "success".equals(result);
    }
}
