package com.bestbuy.controller;

import com.bestbuy.model.Product;
import com.bestbuy.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private ProductServiceImpl productService;

    @Test
    void getProductByIdTest() throws Exception {
        Long id = 1L;
        Product product = mockProduct();
        Mockito.when(productService.getProductById(id)).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
        Mockito.verify(productService, Mockito.times(1)).getProductById(id);
    }

    @Test
    void getProductListTest() throws Exception {
        List<Product> list = mockProductList();
        Mockito.when(productService.getProductList()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
        Mockito.verify(productService, Mockito.times(1)).getProductList();
    }

    @Test
    void buyProductTest() throws Exception {
        Product product = mockProduct();
        Mockito.when(productService.buyProduct(product)).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/buyProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))) // send the product JSON as request body
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(productService, Mockito.times(1)).buyProduct(product);
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
