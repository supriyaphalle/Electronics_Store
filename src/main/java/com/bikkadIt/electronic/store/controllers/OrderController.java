package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.dtos.*;
import com.bikkadIt.electronic.store.services.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * @param orderRequest
     * @return newly created order
     * @author SUPRIYA
     * @apiNote To create order in database
     * @since V 1.0
     */
    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        logger.info("Entering Request to create order Data");
        OrderDto orderDto = orderService.createOrder(orderRequest);
        logger.info("Completed Request to create category Data");
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    /**
     * @param orderId
     * @return return api message after order removed
     * @author SUPRIYA
     * @apiNote To remove order
     * @since V 1.0
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        logger.info("Entering request to delete order data for id:{}", orderId);
        orderService.removeOrder(orderId);
        ApiResponseMessage message = ApiResponseMessage.builder().status(HttpStatus.OK).message("order is removed!!")
                .success(true).build();
        logger.info("Completed request to delete order data for id:{}", orderId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * @param  userId
     * @return get all order of user data
     * @author SUPRIYA
     * @apiNote To get order data from  database with userID
     * @since V 1.0
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId) {
        logger.info("Entering request to get  order data for userID: {} ", userId);
        List<OrderDto> orderList = orderService.getOrderOfUser(userId);
        logger.info("Completed request to get  order data for userID: {} ", userId);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    /**
     * @return get all order data
     * @author SUPRIYA
     * @apiNote To get order data from  database
     * @since V 1.0
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrder(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "billingName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Entering request to get all order data ");
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed request to get all order data ");
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
