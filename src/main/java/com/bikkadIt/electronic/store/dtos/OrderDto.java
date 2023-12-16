package com.bikkadIt.electronic.store.dtos;

import com.bikkadIt.electronic.store.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderDto {


    private String orderId;

    private String orderStatus = "PENDING";

    private String paymentStatus = "NOTPAID";

    private int orderAmount;

    private String billingName;

    private String billingAddress;

    private String billingPhone;

    private Date orderDate;

    private Date deliveredDate;

    private List<OrderItemDto> orderItems = new ArrayList<>();


}
