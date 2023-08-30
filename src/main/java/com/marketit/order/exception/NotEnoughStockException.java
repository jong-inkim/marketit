package com.marketit.order.exception;

import com.marketit.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NotEnoughStockException extends CustomException {
    public NotEnoughStockException(String message, HttpStatus status) {
        super(message, status);
    }

    public NotEnoughStockException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
