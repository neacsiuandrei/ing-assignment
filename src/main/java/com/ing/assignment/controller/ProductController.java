package com.ing.assignment.controller;

import com.ing.assignment.domain.Product;
import com.ing.assignment.exceptions.EmptyProductNameException;
import com.ing.assignment.exceptions.NegativePriceException;
import com.ing.assignment.exceptions.ProductNotFoundException;
import com.ing.assignment.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

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
        LOGGER.info("Received request to add product with {} name and {} price...", productName, productPrice);
        if(productName == null || productName.trim().isEmpty()){
            LOGGER.error("Product name was empty, process has been aborted.");
            throw new EmptyProductNameException();
        }
        if(productPrice<0){
            LOGGER.error("Product price was negative, process has been aborted.");
            throw new NegativePriceException(productPrice);
        }
        Product newProduct = productService.addProduct(productName, productPrice);
        LOGGER.info("Product with {} id, {} name and {} price has been added.", newProduct.getId(), newProduct.getName(), newProduct.getPrice());
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId){
        LOGGER.info("Received request to fetch product with id {}...", productId);
        Optional<Product> newProduct = productService.getProductById(productId);
        if(newProduct.isPresent()){
            LOGGER.info("Product successfully fetched.");
            return new ResponseEntity<>(newProduct.get(), HttpStatus.OK);
        }
        else {
            LOGGER.error("No product found with id {}. Process has been aborted.", productId);
            throw new ProductNotFoundException(productId);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        LOGGER.info("Received request to fetch all products...");
        List<Product> products = productService.getAllProducts();
        LOGGER.info("Fetched a total of {} products.", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/changePrice")
    public ResponseEntity<Product> changeProductPrice(@RequestParam Long id, @RequestParam Double productPrice){
        LOGGER.info("Received request to change price of product with id {}...", id);
        Product product = productService.changePriceOfProduct(id, productPrice);
        if(product==null){
            LOGGER.error("No product found with id {}. Process has been aborted.", id);
            throw new ProductNotFoundException(id);
        }
        if(productPrice<0){
            LOGGER.error("Product price was negative, process has been aborted.");
            throw new NegativePriceException(productPrice);
        }
        LOGGER.info("Successfully changed the price to {} for product {}", product.getPrice(), product.getId());
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteProductById(@RequestParam Long productId){
        LOGGER.info("Received request to delete product with id {}...", productId);
        if(!productService.deleteProduct(productId)){
            throw new ProductNotFoundException(productId);
        }
        LOGGER.info("Product with id {} has been deleted.", productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
