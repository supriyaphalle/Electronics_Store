package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.dtos.*;
import com.bikkadIt.electronic.store.services.OrderService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        OrderDto orderDto = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMessage message = ApiResponseMessage.builder().status(HttpStatus.OK).message("order is removed!!")
                .success(true).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId) {
        List<OrderDto> orderList = orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrder(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "billingName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
