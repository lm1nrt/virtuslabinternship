package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public class TenPercentDiscount {

    public static String NAME = "TenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt) || shouldApplyWhenActiveDiscount(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.9));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        return receipt.totalPrice().compareTo(BigDecimal.valueOf(50)) >= 0;
    }
    private boolean shouldApplyWhenActiveDiscount(Receipt receipt){
        return (receipt.discounts().contains("FifteenPercentDiscount") &&
                receipt.totalPrice().divide(BigDecimal.valueOf(0.85)).compareTo(BigDecimal.valueOf(50)) >= 0);
    }
}
