package com.bikkadIt.electronic.store.service;


import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.repositories.UserRepository;
import com.bikkadIt.electronic.store.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    ModelMapper mapper;
    User user;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void init() {

       user= User.builder()
                .name("Supriya")
                .email("supriyaPhalle@gmail.com")
                .about("this is Create method")
                .gender("Female")
                .imageName("abc.png")
                .password("abcd")
                .build();


    }

    @Test
    public void createUserTest() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
       System.out.println(user1.getName());
        Assertions.assertNotNull(user1);

        Assertions.assertEquals("Supriya",user1.getName());
    }


    @Test
    public void updateUserTest(){

        String userID="qazxswedc";

        UserDto userDto=UserDto.builder()
                .name("Priya Kalekar")
                .about("This is update data")
                .gender("Female")
                .imageName("abc.jpg")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userService.updateUSer(userDto,userID);
        System.out.println(updateUser.getName());
        Assertions.assertNotNull(userDto);

        Assertions.assertEquals(userDto.getName(),updateUser.getName(),"Name is not equal");



    }

}
