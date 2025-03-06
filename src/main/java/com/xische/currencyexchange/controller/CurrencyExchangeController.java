package com.xische.currencyexchange.controller;

import com.xische.currencyexchange.model.BillDetails;
import com.xische.currencyexchange.service.CurrencyExchangeService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/currency-exchange")
public class CurrencyExchangeController {

    private static final Logger log = LoggerFactory.getLogger(CurrencyExchangeController.class);

    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    public CurrencyExchangeController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @RateLimiter(name = "basic")
    @PostMapping("/calculate")
    public Mono<Double> calculatePayableAmount(@Validated @RequestBody BillDetails billDetails) {
        log.info("Received request to calculate payable amount for bill details: {}", billDetails);
        return currencyExchangeService.calculatePayableAmount(billDetails)
                .doOnSuccess(amount -> log.info("Calculated payable amount: {}", amount))
                .doOnError(e -> log.error("Error calculating payable amount", e));
    }
}