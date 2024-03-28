package com.ing.assignment.services;

import com.ing.assignment.domain.Product;
import com.ing.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(String name, float price){
        return  productRepository.save(new Product(name, price));
    }

}
