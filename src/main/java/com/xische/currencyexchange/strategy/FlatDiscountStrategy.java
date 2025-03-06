package com.xische.currencyexchange.strategy;

public class FlatDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        int discount = (int) (totalAmount / 100) * 5; // $5 discount for every $100
        return totalAmount - discount;
    }
}