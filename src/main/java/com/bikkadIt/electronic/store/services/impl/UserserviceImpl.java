package com.bikkadIt.electronic.store.services.impl;

import com.bikkadIt.electronic.store.constant.AppConstants;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.entities.User;
import com.bikkadIt.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadIt.electronic.store.helper.Helper;
import com.bikkadIt.electronic.store.repositories.UserRepository;
import com.bikkadIt.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserserviceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(UserserviceImpl.class);


    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Initiating the dao call for the save user data");
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = dtoToEntity(userDto);
        User user1 = userRepository.save(user);

        UserDto userDto1 = entityToDto(user1);
        logger.info("Complete the dao call for the save user data");
        return userDto1;
    }

    @Override
    public UserDto updateUSer(UserDto userDto, String userId) {
        logger.info("Initiating the dao call for the update user data for id :{} ", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User save = userRepository.save(user);
        UserDto updateDto = entityToDto(save);
        logger.info("Completed the dao call for the update user data for id :{} ", userId);
        return updateDto;
    }

    @Override
    public void deleteUSer(String userId) {
        logger.info("Initiating the dao call for the delete user data for id :{} ", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        userRepository.delete(user);
        logger.info("Completed the dao call for the delete user data for id :{} ", userId);
    }

    @Override
    public PageableResponse<UserDto> getAllUSer(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating the dao call for the get user data ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);
        logger.info("Completed the dao call for the get user data");
        return pageableResponse;
    }

    @Override
    public UserDto getUSerById(String userId) {
        logger.info("Initiating the dao call for the get user data for id :{} ", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + "with userID"));
        logger.info("Completed the dao call for the get user data for id :{] ", userId);
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating the dao call for the get user data for email : {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND_WITH_EMAIL));
        logger.info("Completed the dao call for the get user data for email :{} ", email);
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiating the dao call for the search user data for keyword :{}", keyword);
        List<User> userList = userRepository.findByNameContaining(keyword);
        List<UserDto> userDtoList = userList.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        logger.info("Completed the dao call for the search user data for keyword :{} ", keyword);
        return userDtoList;
    }

    private UserDto entityToDto(User user) {

        return mapper.map(user, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }
}
