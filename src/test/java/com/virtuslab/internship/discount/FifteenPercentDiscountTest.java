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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenPercentDiscountTest implements ICalculatePrice {

    @DisplayName("Test should apply 15 percent discount when there are at least 3 grains products on one receipt entry")
    @Test
    void shouldApply15PercentDiscountWhenThereAre3GrainsProductsOnReceiptEntry(){
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 3));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(3)).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
        assertEquals("FifteenPercentDiscount", receiptAfterDiscount.discounts().get(0));
    }
    @DisplayName("Test should apply 15 percent discount when there are at least 3 grains products on receipt entries")
    @Test
    void shouldApply15PercentDiscountWhenThereAre3GrainsProductsOnReceiptEntries(){
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(bread, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(3)).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
        assertEquals("FifteenPercentDiscount", receiptAfterDiscount.discounts().get(0));
    }
    @DisplayName("Test should not apply 15 percent discount when there are no 3 grains products on receipt entry")
    @Test
    void shouldNotApply15PercentDiscountWhenThereAreNo3GrainsProductsOnReceiptEntry() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(2));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
    @DisplayName("Test should apply 15 percent discount when there are at least 3 grains product on receipt")
    @Test
    void shouldApply15PercentDiscountWhenThereAre3GrainsProductsOnReceipt(){
        //Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var bread = productDb.getProduct("Bread");

        cart.addProduct(bread);
        cart.addProduct(bread);
        cart.addProduct(bread);

        var expectedTotalPrice = calculatePrice(cart.getProducts()).multiply(BigDecimal.valueOf(0.85));
        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);
        //Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
        assertEquals(Arrays.asList("FifteenPercentDiscount"), receipt.discounts());
    }
    @DisplayName("Test should not apply 15 percent discount when there are no 3 grains products on receipt")
    @Test
    void shouldNotApply15PercentDiscountWhenThereAreNo3GrainsProductsOnReceipt(){
        //Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var bread = productDb.getProduct("Bread");

        cart.addProduct(bread);
        cart.addProduct(bread);

        var expectedTotalPrice = calculatePrice(cart.getProducts());
        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);
        //Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }


}
