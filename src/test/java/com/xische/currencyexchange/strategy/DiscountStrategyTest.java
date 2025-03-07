package com.xische.currencyexchange.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountStrategyTest {

    @Test
    public void testAffiliateDiscountStrategy() {
        DiscountStrategy strategy = new AffiliateDiscountStrategy();
        double totalAmount = 100.0;
        double expected = 90.0; // 10% discount
        assertEquals(expected, strategy.applyDiscount(totalAmount));
    }

    @Test
    public void testFlatDiscountStrategy() {
        DiscountStrategy strategy = new FlatDiscountStrategy();
        double totalAmount = 250.0;
        double expected = 240.0; // $5 discount for every $100
        assertEquals(expected, strategy.applyDiscount(totalAmount));
    }

    @Test
    public void testNoDiscountStrategy() {
        DiscountStrategy strategy = new NoDiscountStrategy();
        double totalAmount = 100.0;
        double expected = 100.0; // No discount
        assertEquals(expected, strategy.applyDiscount(totalAmount));
    }

    @Test
    public void testLoyalCustomerDiscountStrategy() {
        DiscountStrategy strategy = new LoyalCustomerDiscountStrategy();
        double totalAmount = 100.0;
        double expected = 95.0; // 5% discount
        assertEquals(expected, strategy.applyDiscount(totalAmount));
    }

    @Test
    public void testPercentageDiscountStrategy() {
        DiscountStrategy strategy = new PercentageDiscountStrategy();
        double totalAmount = 100.0;
        double expected = 90.0; // 10% discount
        assertEquals(expected, strategy.applyDiscount(totalAmount));
    }

    @Test
    public void testEmployeeDiscountStrategy() {
        DiscountStrategy strategy = new EmployeeDiscountStrategy();
        double totalAmount = 100.0;
        double expected = 70.0; // 30% discount
        assertEquals(expected, strategy.applyDiscount(totalAmount));
    }
}