package com.ing.assignment.controller;

import com.ing.assignment.domain.Product;
import com.ing.assignment.exceptions.NegativePriceException;
import com.ing.assignment.exceptions.ProductNotFoundException;
import com.ing.assignment.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Product> addProduct(@RequestParam String productName, @RequestParam Double productPrice){
        if(productPrice<0){
            throw new NegativePriceException(productPrice);
        }
        Product newProduct = productService.addProduct(productName, productPrice);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId){
        Optional<Product> newProduct = productService.getProductById(productId);
        if(newProduct.isPresent()){
            return new ResponseEntity<>(newProduct.get(), HttpStatus.OK);
        }
        else {
            throw new ProductNotFoundException(productId);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/changePrice")
    public ResponseEntity<Product> changeProductPrice(@RequestParam Long id, @RequestParam Double productPrice){
        Product product = productService.changePriceOfProduct(id, productPrice);
        if(product==null){
            throw new ProductNotFoundException(id);
        }
        if(productPrice<0){
            throw new NegativePriceException(productPrice);
        }
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

}
