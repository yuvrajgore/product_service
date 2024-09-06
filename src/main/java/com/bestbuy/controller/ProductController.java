package com.bestbuy.controller;

import com.bestbuy.model.Product;
import com.bestbuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    ResponseEntity<List<Product>> getProductList() {
        List<Product> productList = productService.getProductList();
        return productList.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(productList);
    }
    @PostMapping("/buyProduct")
    ResponseEntity<Product> buyProduct(@RequestBody Product product) {
        Product updatedProduct = productService.buyProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }
    @GetMapping("/{id}")
    ResponseEntity<Product> getProductById(@PathVariable Long id) {
       Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}
