package com.xische.currencyexchange.model;

public record TokenResponse(String tokenType, String accessToken, long expiresIn, String refreshToken) {}