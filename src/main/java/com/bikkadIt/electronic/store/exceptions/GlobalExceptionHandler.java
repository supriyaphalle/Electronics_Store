package com.bikkadIt.electronic.store.exceptions;


import com.bikkadIt.electronic.store.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> ResourceNotFoundException(ResourceNotFoundException ex) {

        logger.info("Exception Hander invoked !!");
        ApiResponseMessage respMessage = ApiResponseMessage.builder().message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity(respMessage, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        HashMap<String, Object> map = new HashMap<>();
        allErrors.stream().forEach(objectError -> {

            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            map.put(field, message);
        });

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> BadApiRequestException(BadApiRequestException ex) {

        logger.info("Bad api request !!");
        ApiResponseMessage respMessage = ApiResponseMessage.builder().message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity(respMessage, HttpStatus.BAD_REQUEST);
    }








}
