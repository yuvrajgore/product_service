package com.bestbuy.service;

import com.bestbuy.exception.FailedToUpdateProductException;
import com.bestbuy.exception.ProductNotFoundException;
import com.bestbuy.model.Product;
import com.bestbuy.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void getProductListTest() {
        List<Product> list = mockProductList();
        Mockito.when(productRepository.findAll()).thenReturn(list);
        List<Product> response = productService.getProductList();
        Assertions.assertEquals(list.size(), response.size());
    }

    @Test
    void buyProductTest() {
        Product product = mockProduct();
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Product response = productService.buyProduct(product);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(product.getName(), response.getName());
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    void buyProduct_FailTest() {
        String message = "Failed to update product";
        Product product = mockProduct();
        product.setName(null);
        Mockito.when(productService.buyProduct(product)).thenThrow(new FailedToUpdateProductException(message));
        FailedToUpdateProductException exception = Assertions.assertThrows(FailedToUpdateProductException.class, () -> productService.buyProduct(product));
        Assertions.assertEquals(message, exception.getMessage());
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    void getProductByIdTest() {
        Long id = 1L;
        Product product = mockProduct();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.ofNullable(product));
        Product response = productService.getProductById(id);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(product.getName(), response.getName());
        Mockito.verify(productRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void getProductById_FailTest() {
        Long id = 1L;
        String message = "Product not found with id:" + id;
        Product product = mockProduct();
        product.setName(null);
        ProductNotFoundException exception = Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));
        Assertions.assertEquals(message, exception.getMessage());
        Mockito.verify(productRepository, Mockito.times(1)).findById(id);
    }
    protected Product mockProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test_1");
        product.setPrice(100L);
        return product;
    }

    protected List<Product> mockProductList() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test_1");
        product.setPrice(100L);
        Product product1 = new Product();
        product.setId(2L);
        product.setName("Test_2");
        product.setPrice(200L);
        List<Product> list = new ArrayList<>();
        list.add(product);
        list.add(product1);
        return list;
    }
}
