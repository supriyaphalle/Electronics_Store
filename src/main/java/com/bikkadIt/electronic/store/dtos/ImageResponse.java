package com.bikkadIt.electronic.store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private String imageName;

    private String message;

    private boolean success;

    private HttpStatus status;
}