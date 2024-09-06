package com.bestbuy.service;

import com.bestbuy.model.Product;
import com.bestbuy.exception.FailedToUpdateProductException;
import com.bestbuy.exception.ProductNotFoundException;
import com.bestbuy.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public Product buyProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            log.error("Failed to update product: {}", e);
            throw new FailedToUpdateProductException("Failed to update product");
        }
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id:" + id));
    }
}
