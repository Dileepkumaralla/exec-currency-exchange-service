package com.xische.currencyexchange.strategy;

public class EmployeeDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * 0.70; // 30% discount
    }
}