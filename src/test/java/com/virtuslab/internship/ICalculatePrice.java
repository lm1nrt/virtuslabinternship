package com.virtuslab.internship;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;
import java.util.List;

public interface ICalculatePrice {
    default BigDecimal calculatePrice(List<Product> products){
        return products.stream()
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
