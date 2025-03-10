package com.xische.currencyexchange.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Data
@Configuration
public class WebClientConfig {

    @Value("${exchange-api.api-url}")
    private String exchangeRateApiUrl;

    @Value("${exchange-api.api-call-limit}")
    private Integer timeoutInSeconds;


    @Bean
    public WebClient exchangeRateClient() {
        return WebClient.builder()
                .baseUrl(this.exchangeRateApiUrl)
                .clientConnector(this.clientHttpConnector())
                .build();
    }

    public ReactorClientHttpConnector clientHttpConnector() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutInSeconds * 1000)
                .responseTimeout(Duration.ofSeconds(timeoutInSeconds))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(timeoutInSeconds))
                        .addHandlerLast(new WriteTimeoutHandler(timeoutInSeconds)));

        return new ReactorClientHttpConnector(httpClient);
    }
}