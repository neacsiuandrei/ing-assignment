package com.ing.assignment.controller;

import com.ing.assignment.domain.Product;
import com.ing.assignment.exceptions.EmptyProductNameException;
import com.ing.assignment.exceptions.NegativePriceException;
import com.ing.assignment.exceptions.ProductNotFoundException;
import com.ing.assignment.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckConnection() {
        ResponseEntity<HttpStatus> httpStatusResponseEntity = productController.checkConnection();
        assertEquals(HttpStatus.OK, httpStatusResponseEntity.getStatusCode());
    }

    @Test
    void testAddProduct() {
        Product expectedProduct = new Product("MockProd1", 123.23);
        when(productService.addProduct("MockProd1", 123.23)).thenReturn(expectedProduct);
        ResponseEntity<Product> productResponseEntity = productController.addProduct("MockProd1", 123.23);

        assertEquals(HttpStatus.CREATED, productResponseEntity.getStatusCode());
        assertEquals(expectedProduct, productResponseEntity.getBody());
    }

    @Test
    void testAddProductWithNullName() {
        assertThrows(EmptyProductNameException.class, () -> {
            productController.addProduct(null, 20.0);
        });
    }

    @Test
    void testAddProductWithEmptyName() {
        assertThrows(EmptyProductNameException.class, () -> {
            productController.addProduct("", 20.0);
        });
    }

    @Test
    void testAddProductWithBlankSpacesName() {
        assertThrows(EmptyProductNameException.class, () -> {
            productController.addProduct("   ", 20.0);
        });
    }

    @Test
    void testAddProductWithNegativePrice() {
        assertThrows(NegativePriceException.class, () -> {
            productController.addProduct("MockProd1", -20.0);
        });
    }

    @Test
    void testGetProductById() {

        Product expectedProduct = new Product("MockProd1", 123.23);
        when(productService.getProductById(1L)).thenReturn(Optional.of(expectedProduct));

        ResponseEntity<Product> productResponseEntity = productController.getProductById(1L);
        assertEquals(HttpStatus.OK, productResponseEntity.getStatusCode());
        assertEquals(expectedProduct, productResponseEntity.getBody());
    }

    @Test
    void testGetProductByIdWhenNotfound() {

        when(productService.getProductById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> {
            productController.getProductById(1L);
        });

    }

    @Test
    void testGetAllProducts() {
        Product mockProduct1 = new Product("MockProd1", 1.0);
        Product mockProduct2 = new Product("MockProd2", 2.0);
        List<Product> mockProductList = new ArrayList<>();
        mockProductList.add(mockProduct1);
        mockProductList.add(mockProduct2);
        when(productService.getAllProducts()).thenReturn(mockProductList);

        ResponseEntity<List<Product>> listResponseEntity = productController.getAllProducts();
        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        assertEquals(mockProductList, listResponseEntity.getBody());
    }

    @Test
    void testChangeProductPrice() {
        Product mockProduct1 = new Product("MockProd1", 200.0);
        when(productService.changePriceOfProduct(1L, 200.0)).thenReturn(mockProduct1);

        ResponseEntity<Product> productResponseEntity = productController.changeProductPrice(1L, 200.0);
        assertEquals(HttpStatus.ACCEPTED, productResponseEntity.getStatusCode());
        assertEquals(mockProduct1, productResponseEntity.getBody());
    }

    @Test
    void testChangeProductPriceWhenProductNotFound() {

        when(productService.changePriceOfProduct(1L, 12.3)).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> {
            productController.changeProductPrice(1L, 12.3);
        });

    }

    @Test
    void testChangeProductPriceWhenPriceIsNegative() {

        Product mockProduct1 = new Product("MockProd1", -12.3);
        when(productService.changePriceOfProduct(1L, -12.3)).thenReturn(mockProduct1);

        assertThrows(NegativePriceException.class, () -> {
            productController.changeProductPrice(1L, -12.3);
        });

    }
}