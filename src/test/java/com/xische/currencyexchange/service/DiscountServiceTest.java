package com.xische.currencyexchange.service;

import com.xische.currencyexchange.enums.CurrencyCode;
import com.xische.currencyexchange.model.BillDetails;
import com.xische.currencyexchange.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;
    private BillDetails billDetails;


    @Test
    public void testEmployeeDiscount() {
        billDetails = new BillDetails(
                List.of(
                        new Item("Apple", 1.0, true),
                        new Item("Laptop", 1000.0, false)
                ),
                1001.0,
                true,
                false,
                1,
                CurrencyCode.USD,
                CurrencyCode.EUR
        );

        double finalAmount = discountService.applyDiscounts(billDetails);
        assertEquals(666.0, finalAmount);
    }

    @Test
    public void testAffiliateDiscount() {
        billDetails = new BillDetails(
                List.of(
                        new Item("Apple", 1.0, true),
                        new Item("Laptop", 1000.0, false)
                ),
                1001.0,
                false,
                true,
                1,
                CurrencyCode.USD,
                CurrencyCode.EUR
        );

        double finalAmount = discountService.applyDiscounts(billDetails);
        assertEquals(856.0, finalAmount);
    }

    @Test
    public void testCustomerTenureDiscount() {
        billDetails = new BillDetails(
                List.of(
                        new Item("Apple", 1.0, true),
                        new Item("Laptop", 1000.0, false)
                ),
                1001.0,
                false,
                false,
                3,
                CurrencyCode.USD,
                CurrencyCode.EUR
        );

        double finalAmount = discountService.applyDiscounts(billDetails);
        assertEquals(906.0, finalAmount);
    }

    @Test
    public void testHundredDollarDiscount() {
        billDetails = new BillDetails(
                List.of(
                        new Item("Apple", 1.0, true),
                        new Item("Laptop", 1000.0, false)
                ),
                1001.0,
                false,
                false,
                1,
                CurrencyCode.USD,
                CurrencyCode.EUR
        );

        double finalAmount = discountService.applyDiscounts(billDetails);
        assertEquals(951.0, finalAmount);
    }

    @Test
    public void testNoDiscounts() {
        billDetails = new BillDetails(
                List.of(
                        new Item("Apple", 1.0, true),
                        new Item("Laptop", 1000.0, false)
                ),
                1001.0,
                false,
                false,
                1,
                CurrencyCode.USD,
                CurrencyCode.EUR
        );

        double finalAmount = discountService.applyDiscounts(billDetails);
        assertEquals(951.0, finalAmount);
    }
}
