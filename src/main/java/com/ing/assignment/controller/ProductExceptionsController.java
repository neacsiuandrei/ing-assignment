package com.ing.assignment.controller;

import com.ing.assignment.exceptions.NegativePriceException;
import com.ing.assignment.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ProductExceptionsController {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleProductNotFoundException(ProductNotFoundException e) {
        return "NOT FOUND EXCEPTION: " + e.getMessage();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        return "ARGUMENT TYPE MISMATCH EXCEPTION: " + e.getMessage();
    }

    @ExceptionHandler(NegativePriceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleProductNotFoundException(NegativePriceException e) {
        return "NEGATIVE PRICE EXCEPTION: " + e.getMessage();
    }

}
