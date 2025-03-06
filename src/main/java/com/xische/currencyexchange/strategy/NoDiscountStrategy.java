package com.xische.currencyexchange.strategy;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount;
    }
}
