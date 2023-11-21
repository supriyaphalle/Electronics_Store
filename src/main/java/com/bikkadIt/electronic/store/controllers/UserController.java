package com.bikkadIt.electronic.store.controllers;

import com.bikkadIt.electronic.store.constant.AppConstants;
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

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * @param userDto
     * @return http status for save data
     * @author SUPRIYA
     * @apiNote To save user data in  database
     * @since V 1.0
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Entering Request to create user Data");
        UserDto user = userService.createUser(userDto);
        logger.info("Completed Request to create user Data");
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    /**
     * @param userId
     * @return http status for update data
     * @author SUPRIYA
     * @apiNote To update user data in  database based on id
     * @since V 1.0
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @Valid @RequestBody UserDto userDto) {
        logger.info("Entering Request to update user Data for userId:{} ", userId);
        UserDto userDto1 = userService.updateUSer(userDto, userId);
        logger.info("Completed Request to update user Data for userId:{} ", userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return http status for delete data
     * @author SUPRIYA
     * @apiNote To delete user data in  database based on id
     * @since V 1.0
     */
    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        logger.info("Entering Request to delete user Data for userId:{} ", userId);
        userService.deleteUSer(userId);
        ApiResponseMessage message = new ApiResponseMessage(AppConstants.DELETE_RESPONSE, true, HttpStatus.OK);
        logger.info("Completed Request to delete user Data  for userId:{} ", userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * @param
     * @return list of all user data
     * @author SUPRIYA
     * @apiNote To get user data from  database
     * @since V 1.0
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUSer(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering Request to get user Data");
        PageableResponse userDtoList = userService.getAllUSer(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed Request to get user Data");
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return user data for userId
     * @author SUPRIYA
     * @apiNote To get user data from  database based on id
     * @since V 1.0
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        logger.info("Entering Request to get  user Data with id:{} ", userId);
        UserDto user = userService.getUSerById(userId);
        logger.info("Completed Request to get user Data with id:{} ", userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * @param
     * @return list of user data
     * @author SUPRIYA
     * @apiNote To get user data from  database with email
     * @since V 1.0
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Entering Request to get  user Data with email:{} ", email);
        UserDto userDto = userService.getUserByEmail(email);
        logger.info("Completed Request to get user Data with email:{} ", email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * @param
     * @return list of user data
     * @author SUPRIYA
     * @apiNote To search user data from  database
     * @since V 1.0
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUserByKeyword(@PathVariable String keyword) {
        logger.info("Entering Request to get  user Data with keyword:{} " + keyword);
        List<UserDto> userDtos = userService.searchUser(keyword);
        logger.info("Completed Request to get user Data with keyword:{} " + keyword);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }


}
