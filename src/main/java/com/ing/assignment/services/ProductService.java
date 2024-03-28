package com.ing.assignment.services;

import com.ing.assignment.domain.Product;
import com.ing.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(String name, float price){
        Product newProduct = new Product(name, price);
        System.out.println("Product created with ID: " + newProduct.getId());
        return  productRepository.save(newProduct);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

}
