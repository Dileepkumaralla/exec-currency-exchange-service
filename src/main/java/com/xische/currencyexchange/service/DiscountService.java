package com.xische.currencyexchange.service;

import com.xische.currencyexchange.model.BillDetails;
import com.xische.currencyexchange.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    public double applyDiscounts(BillDetails billDetails) {
        List<Item> items = billDetails.items();


        double nonGroceryAmount = items.stream()
                .filter(item -> !item.isGrocery())
                .mapToDouble(Item::price)
                .sum();


        double discountedAmount = nonGroceryAmount;
        if (billDetails.isEmployee()) {
            discountedAmount = nonGroceryAmount * 0.70; // 30% discount
        } else if (billDetails.isAffiliate()) {
            discountedAmount = nonGroceryAmount * 0.90; // 10% discount
        } else if (billDetails.customerTenure() > 2) {
            discountedAmount = nonGroceryAmount * 0.95; // 5% discount
        }


        double groceryAmount = items.stream()
                .filter(Item::isGrocery)
                .mapToDouble(Item::price)
                .sum();

        double finalAmount = discountedAmount + groceryAmount;


        int hundredDollarDiscounts = (int) (finalAmount / 100);
        finalAmount -= hundredDollarDiscounts * 5;

        return finalAmount;
    }
}