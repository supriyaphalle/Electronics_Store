package com.bikkadIt.electronic.store.services;

import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUSer(UserDto userDto, String userId);

    void deleteUSer(String userId);

    PageableResponse<UserDto> getAllUSer(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDto getUSerById(String userId);

    UserDto getUserByEmail(String email);

    List<UserDto> searchUser(String keyword);

}
