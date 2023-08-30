package com.marketit.order.web;

import com.marketit.order.dto.OrderRequestDto;
import com.marketit.order.dto.OrderResponseDto;
import com.marketit.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order", description = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 접수",
            responses = {
                @ApiResponse(responseCode = "200", description = "정상", content= @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "404", description = "조회된 상품이 존재하지 않습니다.", content= @Content(mediaType = "application/json"))
            })
    @PostMapping
    public ResponseEntity<ResponseForm> receiveOrder(@RequestBody List<OrderRequestDto> requestDtos) {
        Long orderId = orderService.receiveOrder(requestDtos);
        return ResponseEntity.ok(new ResponseForm(HttpStatus.OK, orderId));
    }

    @Operation(summary = "주문 완료 처리",
            responses = {
                    @ApiResponse(responseCode = "200", description = "정상", content= @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "조회된 주문이 없습니다.", content= @Content(mediaType = "application/json"))
    })
    @PutMapping("/complete/{orderId}")
    public ResponseEntity<ResponseForm> completeOrder(@Parameter(name="orderId", description = "주문 번호", in = ParameterIn.PATH) @PathVariable("orderId") Long orderId) {
        orderService.completeOrder(orderId);
        return ResponseEntity.ok(new ResponseForm(HttpStatus.OK, orderId));
    }

    @Operation(summary = "주문 단건 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "정상", content = @Content(schema = @Schema(implementation = OrderResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "조회된 주문이 없습니다.", content= @Content(mediaType = "application/json"))
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseForm> findOne(@Parameter(name="orderId", description = "주문번호", in = ParameterIn.PATH)@PathVariable("orderId") Long orderId) {
        OrderResponseDto responseDto = orderService.findByOrderId(orderId);
        return ResponseEntity.ok(new ResponseForm(HttpStatus.OK, responseDto));
    }

    @Operation(summary = "주문 전체 조회",
        responses = {
            @ApiResponse(responseCode = "200", description = "정상", content = @Content(schema = @Schema(implementation = OrderResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<ResponseForm> findAll() {
        List<OrderResponseDto> orders = orderService.findAll();
        return ResponseEntity.ok(new ResponseForm(HttpStatus.OK, orders));
    }
}
