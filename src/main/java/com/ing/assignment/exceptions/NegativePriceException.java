package com.ing.assignment.exceptions;

public class NegativePriceException extends RuntimeException{

    public NegativePriceException(Double price) {
        super("The price " + price + " is negative. Only values bigger or equal to 0 allowed.");
    }
}
