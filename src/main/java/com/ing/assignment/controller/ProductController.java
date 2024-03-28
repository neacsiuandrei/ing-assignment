package com.ing.assignment.controller;

import com.ing.assignment.domain.Product;
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

}
