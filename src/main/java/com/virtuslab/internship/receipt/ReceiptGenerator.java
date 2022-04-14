package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;

import java.util.*;


public class ReceiptGenerator {
    private List<ReceiptEntry> receiptEntries;
    public ReceiptGenerator(){
        receiptEntries = new ArrayList<>();
    }
    public Receipt generate(Basket basket) {
        FifteenPercentDiscount fifteenPercentDiscount = new FifteenPercentDiscount();
        TenPercentDiscount tenPercentDiscount = new TenPercentDiscount();
        HashMap<Product,Integer> hashMap = new HashMap<>();

        eliminateDuplicates(basket.getProducts(),hashMap);
        hashMap.forEach((k,m) -> receiptEntries.add(new ReceiptEntry(k,m)));

        Receipt newReceipt = fifteenPercentDiscount.apply(new Receipt(receiptEntries));

        return tenPercentDiscount.apply(newReceipt);
    }

    private HashMap<Product,Integer> eliminateDuplicates(List<Product> products,HashMap<Product,Integer> hashMap){
        products.forEach(p -> {
            if(hashMap.containsKey(p)){
                hashMap.put(p,hashMap.get(p) + 1);
            }else hashMap.put(p,1);
        });
        return hashMap;
    }
    public List<ReceiptEntry> getReceiptEntries(){
        return receiptEntries;
    }

}
