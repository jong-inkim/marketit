package com.marketit.order.web;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ResponseForm {
    private HttpStatus status;
    private Object data;

    private LocalDateTime timestamp;

    public ResponseForm(HttpStatus status, Object data) {
        this.status = status;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
