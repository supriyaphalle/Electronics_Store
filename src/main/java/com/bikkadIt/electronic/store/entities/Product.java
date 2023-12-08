package com.bikkadIt.electronic.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product")
public class Product {

    @Id
    private String productId;

    @Column(name="product_name")
    private String name;

    @Column(name="product_description")
    private String description;

    @Column(name="product_price")
    private int price;

    @Column(name="product_quantity")
    private int quantity;

    @Column(name="product_date")
    private Date addedDate;

    @Column(name="product_live")
    private boolean live;
    @Column(name="product_stock")
    private boolean stock;

    @Column(name="discount_Price")
    private int discountPrice;

    @Column(name = "product_Image")
    private String productImage;

}
