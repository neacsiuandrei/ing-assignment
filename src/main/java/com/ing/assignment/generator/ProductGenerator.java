package com.ing.assignment.generator;

import com.ing.assignment.domain.Product;
import com.ing.assignment.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductGenerator implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductGenerator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        productRepository.save(new Product("TEST PRODUCT 1", 34.66));
        productRepository.save(new Product("TEST PRODUCT 2", 35.1));
    }
}
