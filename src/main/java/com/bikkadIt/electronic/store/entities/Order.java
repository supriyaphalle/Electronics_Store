package com.bikkadIt.electronic.store.entities;


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
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;

    private String orderStatus;

    private String paymentStatus;

    private int orderAmount;

    private String billingName;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private Date orderDate;

    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

}
