package com.xische.currencyexchange.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CURRENCY_LIST_CACHE = "currencyListCache";

    private final CacheManager cacheManager;

    @Autowired
    public CacheConfig(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(cron = "${cache.eviction.cron-expression}")
    public void evictAllCachesAtIntervals() {
        Objects.requireNonNull(cacheManager.getCache(CURRENCY_LIST_CACHE)).clear();
    }

}