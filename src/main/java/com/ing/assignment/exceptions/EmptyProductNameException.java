package com.ing.assignment.exceptions;

public class EmptyProductNameException extends RuntimeException{

    public EmptyProductNameException(){
        super("Product name is empty.");
    }

}
