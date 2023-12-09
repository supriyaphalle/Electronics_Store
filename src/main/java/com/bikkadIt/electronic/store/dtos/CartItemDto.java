package com.bikkadIt.electronic.store.dtos;

import com.bikkadIt.electronic.store.entities.Cart;
import com.bikkadIt.electronic.store.entities.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;

    private ProductDto product;

    private int quantity;

    private int totalPrice;


}
