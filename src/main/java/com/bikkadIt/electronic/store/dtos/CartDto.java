package com.bikkadIt.electronic.store.dtos;

import com.bikkadIt.electronic.store.entities.CartItem;
import com.bikkadIt.electronic.store.entities.User;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private String cartId;

    private Date createdAt;

    private User user;

   private List<CartItemDto> items= new ArrayList<>();
}
