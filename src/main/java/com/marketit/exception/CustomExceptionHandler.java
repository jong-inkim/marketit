package com.marketit.exception;

import com.marketit.order.exception.ItemNotFoundException;
import com.marketit.order.exception.NotEnoughStockException;
import com.marketit.order.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> OrderNotFoundExceptionHandler(OrderNotFoundException e) {
        log.info("OrderNotFoundException Handler = {} ", e.getStatus());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> ItemNotFoundExceptionHandler(ItemNotFoundException e) {
        log.info("ItemNotFoundException Handler = {} ", e.getStatus());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ErrorResponse> NotEnoughStockExceptionHandler(NotEnoughStockException e) {
        log.info("NotEnoughStockException Handler = {} ", e.getStatus());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }
}
