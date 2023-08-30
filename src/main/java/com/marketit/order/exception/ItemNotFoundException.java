package com.marketit.order.exception;

import com.marketit.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends CustomException {

    public ItemNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }

    public ItemNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
