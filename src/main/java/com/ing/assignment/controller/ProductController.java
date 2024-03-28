package com.ing.assignment.controller;

import com.ing.assignment.domain.Product;
import com.ing.assignment.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<HttpStatus> checkConnection(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Product> addProduct(@RequestParam String productName, @RequestParam float productPrice){
        Product newProduct = productService.addProduct(productName, productPrice);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<String> getProductById(@PathVariable Long productId){
        Optional<Product> newProduct = productService.getProductById(productId);
        if(newProduct.isPresent()){
            return new ResponseEntity<>(newProduct.get().toString(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
