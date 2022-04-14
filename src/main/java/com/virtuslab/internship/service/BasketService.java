package com.virtuslab.internship.service;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BasketService {
    public List<Product> getProducts(Basket basket){
        return basket.getProducts();
    }

    public Basket clearBasket(){
        return new Basket();
    }


}
