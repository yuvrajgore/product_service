package com.bestbuy.exception;

public class FailedToUpdateProductException extends RuntimeException{
    public FailedToUpdateProductException(String message) {
        super(message);
    }
}
