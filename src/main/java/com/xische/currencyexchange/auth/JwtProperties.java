package com.xische.currencyexchange.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secretKey = "rzxlszyykpbgqcflzxsqcysyhljt";

    private long validityInMs = 3600000; // 1h

    private long refreshTokenValidityInMs = 86400000; // 24h

    public String getSecretKey() {
        return secretKey;
    }

    public long getValidityInMs() {
        return validityInMs;
    }

    public void setValidityInMs(long validityInMs) {
        this.validityInMs = validityInMs;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getRefreshTokenValidityInMs() {
        return refreshTokenValidityInMs;
    }

    public void setRefreshTokenValidityInMs(long refreshTokenValidityInMs) {
        this.refreshTokenValidityInMs = refreshTokenValidityInMs;
    }
}
