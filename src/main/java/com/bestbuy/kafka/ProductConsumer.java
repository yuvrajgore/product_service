package com.bestbuy.kafka;

import com.bestbuy.model.Product;
import com.bestbuy.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductConsumer {

    @Autowired
    private ProductService productService;
    @KafkaListener(topics = "pocProduct", groupId = "consumerGroup")
    public void consume1(Product product) {
        log.info("Message received: {}", product);
        productService.buyProduct(product);
    }
    @KafkaListener(topics = "pocStringTopic", groupId = "consumerGroup")
    public void consume(String message) {
        log.info(String.format("Message received %s", message));
    }
}
