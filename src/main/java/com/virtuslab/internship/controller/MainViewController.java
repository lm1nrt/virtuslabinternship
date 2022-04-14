package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import com.virtuslab.internship.service.BasketService;
import com.virtuslab.internship.service.ProductService;
import com.virtuslab.internship.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainViewController {


    private BasketService basketService;
    private ProductService productService;
    private ReceiptService receiptService;
    private Basket basket;
    @Autowired
    public MainViewController(BasketService basketService,ProductService productService,ReceiptService receiptService){
        this.basketService = basketService;
        this.productService = productService;
        this.receiptService = receiptService;
        basket = new Basket();
    }

    @GetMapping("/mainView")
    public String main(){
        return "redirect:/";
    }
    @GetMapping("/receiptView")
    public String receipt(){
        return "redirect:/receipt";
    }

    @GetMapping("/")
    public String mainView(Model model){
        model.addAttribute("allProducts",productService.getAllProducts());
        model.addAttribute("priceWithoutReceipt",productService.calculatePrice(basket.getProducts()));
        model.addAttribute("names", productService.getProductsName());
        model.addAttribute("currentProducts",basketService.getProducts(basket));
        return "index";
    }
    @GetMapping("/receipt")
    public String bill(Model model){
        var receipt = receiptService.getReceipt(basket);
        model.addAttribute("price",receipt.totalPrice());
        model.addAttribute("myReceipt",receipt.entries());
        model.addAttribute("priceWithoutReceipt",productService.calculatePrice(basket.getProducts()));
        model.addAttribute("discounts",receiptService.getDiscounts(receipt));
        basket = basketService.clearBasket();
        return "receipt";
    }

    @PostMapping("/add")
    public String addProductToBasket(@RequestParam(value = "name") String name,@RequestParam(value = "quantity") int value){ //,@RequestParam(value = "value") int value
        Product product = productService.getByName(name);
        basket.addProduct(product,value);
        return "redirect:/";
    }


    @RequestMapping(value = "/clear", method={RequestMethod.DELETE, RequestMethod.GET})
    public String deleteAll(){
        basket = basketService.clearBasket();
        return "redirect:/";
    }



}

