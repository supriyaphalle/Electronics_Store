package com.bikkadIt.electronic.store.services;

import com.bikkadIt.electronic.store.dtos.CreateOrderRequest;
import com.bikkadIt.electronic.store.dtos.OrderDto;
import com.bikkadIt.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    public OrderDto createOrder(CreateOrderRequest orderDto);

    public void removeOrder(String orderId);

    public List<OrderDto> getOrderOfUser(String userId);

    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

}
