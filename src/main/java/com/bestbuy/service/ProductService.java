package com.bestbuy.service;

import com.bestbuy.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductList();
    Product buyProduct(Product product);

    Product getProductById(Long id);
}
