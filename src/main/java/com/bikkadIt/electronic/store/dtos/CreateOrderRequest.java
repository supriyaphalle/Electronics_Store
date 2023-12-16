package com.bikkadIt.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotBlank(message = "Cart id is required !")
    private String cartId;

    @NotBlank(message = "user id is required !")
    private String userId;


    private String orderStatus = "PENDING";
    private String paymentStatus = "NOTPAID";

    @NotBlank(message = "Address id is required !")
    private String billingAddress;

    @NotBlank(message = "Phone number id is required !")
    private String billingPhone;

    @NotBlank(message = "Name id is required !")
    private String billingName;


}
