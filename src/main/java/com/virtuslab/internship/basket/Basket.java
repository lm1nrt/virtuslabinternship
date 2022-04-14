package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Basket {

    private final List<Product> products;

    public Basket() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(Product product,int howMany) {
        IntStream.range(0,howMany).forEach(p -> products.add(product));
    }

    public List<Product> getProducts() {
        return products;
    }
}
