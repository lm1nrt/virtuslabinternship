package com.virtuslab.internship.service;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService {
    public List<String> getDiscounts(Receipt receipt){
        return receipt.discounts();
    }
    public Receipt getReceipt(Basket cart){
        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);
        return receipt;
    }
}
