package com.bikkadIt.electronic.store.entities;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_password")
    private String password;

    private String gender;

    private String about;

    @Column(name = "user_image_name")
    private String imageName;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> order = new ArrayList<>();


}
