package com.bikkadIt.electronic.store.entities;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "Id")
    private String categoryId;

    @Column(name = "category_title")
    private String title;

    @Column(name = "category_desc")
    private String description;

    @Column(name = "cover_Image")
    private String coverImage;

}
