package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import java.math.BigDecimal;

public class FifteenPercentDiscount {
    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt) >= 3) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private int shouldApply(Receipt receipt) {
        return receipt.entries().stream()
                .filter(r -> r.product().type() == Product.Type.GRAINS)
                    .map(ReceiptEntry::quantity)
                    .reduce(0,Integer::sum);
    }
}
