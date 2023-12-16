package com.bikkadIt.electronic.store.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderItemDto {

    private int orderItemsId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;

}
