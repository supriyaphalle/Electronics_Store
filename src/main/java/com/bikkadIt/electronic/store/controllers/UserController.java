package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.dtos.ApiResponseMessage;
import com.bikkadIt.electronic.store.dtos.PageableResponse;
import com.bikkadIt.electronic.store.dtos.UserDto;
import com.bikkadIt.electronic.store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * @author SUPRIYA
     * @apiNote To save user data in  database
     * @param userDto
     * @since V 1.0
     * @return http status for save data
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Entering Request to create user Data");
        UserDto user = userService.createUser(userDto);
        logger.info("Completed Request to create user Data");
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    /**
     * @author SUPRIYA
     * @apiNote To update user data in  database based on id
     * @param userId
     * @since V 1.0
     * @return http status for update data
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @Valid @RequestBody UserDto userDto) {
        logger.info("Entering Request to update user Data for userId:{} ",userId);
        UserDto userDto1 = userService.updateUSer(userDto, userId);
        logger.info("Completed Request to update user Data for userId:{} ",userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    /**
     * @author SUPRIYA
     * @apiNote To delete user data in  database based on id
     * @param userId
     * @since V 1.0
     * @return http status for delete data
     */
    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        logger.info("Entering Request to delete user Data for userId:{} ",userId);
        userService.deleteUSer(userId);
        ApiResponseMessage message = new ApiResponseMessage("User is deleted Successfully !!", true, HttpStatus.OK);
        logger.info("Completed Request to delete user Data  for userId:{} ",userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * @author SUPRIYA
     * @apiNote To get user data from  database
     * @param
     * @since V 1.0
     * @return list of all user data
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUSer(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Entering Request to get user Data");
        PageableResponse userDtoList = userService.getAllUSer(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed Request to get user Data");
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    /**
     * @author SUPRIYA
     * @apiNote To get user data from  database based on id
     * @param userId
     * @since V 1.0
     * @return user data for userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        logger.info("Entering Request to get  user Data with id:{} ",userId);
        UserDto user = userService.getUSerById(userId);
        logger.info("Completed Request to get user Data with id:{} ",userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * @author SUPRIYA
     * @apiNote To get user data from  database with email
     * @param
     * @since V 1.0
     * @return list of user data
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Entering Request to get  user Data with email:{} ",email);
        UserDto userDto = userService.getUserByEmail(email);
        logger.info("Completed Request to get user Data with email:{} ",email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    /**
     * @author SUPRIYA
     * @apiNote To search user data from  database
     * @param
     * @since V 1.0
     * @return list of user data
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUserByKeyword(@PathVariable String keyword) {
        logger.info("Entering Request to get  user Data with keyword:{} "+keyword);
        List<UserDto> userDtos = userService.searchUser(keyword);
        logger.info("Completed Request to get user Data with keyword:{} "+keyword);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }


}
