package com.marketit.order.exception;

import com.marketit.exception.CustomException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends CustomException {

    public OrderNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }

    public OrderNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
