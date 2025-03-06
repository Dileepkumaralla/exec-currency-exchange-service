package com.xische.currencyexchange.strategy;

public class LoyalCustomerDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * 0.95; // 5% discount
    }
}
