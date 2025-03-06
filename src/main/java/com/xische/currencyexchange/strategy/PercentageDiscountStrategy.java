package com.xische.currencyexchange.strategy;

public class PercentageDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        // Apply only one percentage-based discount
        double discount = totalAmount * 0.1; // Example: 10% discount
        return totalAmount - discount;
    }
}