package com.virtuslab.internship.service;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ProductService{
    private ProductDb productDb;

    public ProductService(){
        this.productDb = new ProductDb();
    }

    public Set<Product> getAllProducts(){
        return productDb.getProducts();
    }

    public Product getByName(String name){
        return productDb.getProduct(name);
    }

    public List<String> getProductsName(){
        return productDb.getNames();
    }

    public BigDecimal calculatePrice(List<Product> products){
        return  products.stream()
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
