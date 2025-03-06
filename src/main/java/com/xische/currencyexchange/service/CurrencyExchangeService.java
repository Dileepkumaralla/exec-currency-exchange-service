package com.xische.currencyexchange.service;

import com.xische.currencyexchange.model.BillDetails;
import reactor.core.publisher.Mono;

public interface CurrencyExchangeService {
    Mono<Double> calculatePayableAmount(BillDetails billDetails);
}