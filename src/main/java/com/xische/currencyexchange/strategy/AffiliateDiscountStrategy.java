package com.xische.currencyexchange.strategy;

public class AffiliateDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * 0.90; // 10% discount
    }
}
