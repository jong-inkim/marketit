package com.marketit.order.web;

import com.marketit.order.dto.OrderRequestDto;
import com.marketit.order.dto.OrderResponseDto;
import com.marketit.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseForm> receiveOrder(@RequestBody List<OrderRequestDto> requestDtos) {
        Long orderId = orderService.receiveOrder(requestDtos);
        return ResponseEntity.ok(new ResponseForm(HttpStatus.OK, orderId));
    }

    @PostMapping("/complete/{orderId}")
    public ResponseEntity<ResponseForm> completeOrder(@PathVariable("orderId") Long orderId) {
        orderService.completeOrder(orderId);
        return ResponseEntity.ok(new ResponseForm(HttpStatus.OK, orderId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseForm> findOne(@PathVariable("orderId") Long orderId) {
        OrderResponseDto responseDto = orderService.findByOrderId(orderId);
        return ResponseEntity.ok(new ResponseForm(HttpStatus.OK, responseDto));
    }

    @GetMapping
    public ResponseEntity<ResponseForm> findAll() {
        List<OrderResponseDto> orders = orderService.findAll();
        return ResponseEntity.ok(new ResponseForm(HttpStatus.OK, orders));
    }
}
