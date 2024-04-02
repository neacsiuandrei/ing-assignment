package com.ing.assignment.services;

import com.ing.assignment.domain.Product;
import com.ing.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(String name, Double price){
        Product newProduct = new Product(name, price);
        return  productRepository.save(newProduct);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product changePriceOfProduct(Long id, Double newPrice){
        Optional<Product> product = getProductById(id);
        if(product.isPresent()){
            product.get().setPrice(newPrice);
            productRepository.save(product.get());
            return product.get();
        }
        else {
            return null;
        }
    }

    public boolean deleteProduct(Long id){
        Optional<Product> product = getProductById(id);
        if(product.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

}
