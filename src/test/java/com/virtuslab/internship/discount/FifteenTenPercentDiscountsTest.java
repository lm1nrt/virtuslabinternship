package com.virtuslab.internship.discount;

import com.virtuslab.internship.ICalculatePrice;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenTenPercentDiscountsTest implements ICalculatePrice {
    @DisplayName("Test should apply 15 percent discount and 10 percent discount when there are 3 grains product and total price of receipt is above 50")
    @Test
    void shouldApply15PercentDiscountAnd10Percent(){
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var cheese = productDb.getProduct("Cheese");
        var bread = productDb.getProduct("Bread");

        cart.addProduct(bread);
        cart.addProduct(bread);
        cart.addProduct(bread);
        cart.addProduct(cheese);
        cart.addProduct(cheese);

        var expectedTotalPrice = calculatePrice(cart.getProducts()).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));
        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);
        //Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(2, receipt.discounts().size());
        assertEquals(Arrays.asList("FifteenPercentDiscount","TenPercentDiscount"), receipt.discounts());
    }
}
