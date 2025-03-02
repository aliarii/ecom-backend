package com.cry.ecom.Exception;

public class InvalidCouponException extends RuntimeException {
    public InvalidCouponException(String message) {
        super(message);
    }
}
