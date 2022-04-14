package com.virtuslab.internship.discount;

import com.virtuslab.internship.ICalculatePrice;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TenPercentDiscountTest implements ICalculatePrice {

    @Test
    void shouldApply10PercentDiscountWhenPriceIsAbove50() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new TenPercentDiscount();
        var expectedTotalPrice = cheese.price().add(steak.price()).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
        assertEquals("TenPercentDiscount", receiptAfterDiscount.discounts().get(0));
    }

    @Test
    void shouldNotApply10PercentDiscountWhenPriceIsBelow50() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new TenPercentDiscount();
        var expectedTotalPrice = cheese.price().multiply(BigDecimal.valueOf(2));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }

    @DisplayName("Test should apply 10 percent discount when total price of receipt is above 50")
    @Test
    void shouldApply10PercentDiscountWhenPriceIsAbove50OnReceipt() {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var cheese = productDb.getProduct("Cheese");
        var bread = productDb.getProduct("Bread");

        cart.addProduct(bread);
        cart.addProduct(cheese);
        cart.addProduct(cheese);
        cart.addProduct(cheese);


        var expectedTotalPrice = calculatePrice(cart.getProducts()).multiply(BigDecimal.valueOf(0.9));
        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);
        //Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
        assertEquals("TenPercentDiscount", receipt.discounts().get(0));
    }

    @DisplayName("Test should not apply 10 percent discount when total price of receipt is below 50")
    @Test
    void shouldNotApply10PercentDiscountWhenPriceIsBelow50OnReceipt() {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var cheese = productDb.getProduct("Cheese");
        var bread = productDb.getProduct("Bread");

        cart.addProduct(bread);
        cart.addProduct(cheese);

        var expectedTotalPrice = calculatePrice(cart.getProducts());
        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);
        //Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }

}
