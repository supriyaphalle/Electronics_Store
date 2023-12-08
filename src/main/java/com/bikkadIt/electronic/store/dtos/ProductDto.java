package com.bikkadIt.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productId;
    @NotBlank
    @Size(min = 4, message = "title must be of minimum 4 charters")
    private String name;

    @NotBlank(message = "Description required!!")
    @Size(min = 4, message = "Description must be of minimum 4 charters")
    private String description;

    private int price;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private int discountPrice;
    private String productImage;

}
