package com.bikkadIt.electronic.store.dtos;


import com.bikkadIt.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userId;

    @Size(min = 3, max = 20, message = "Invalid Name!!!")
    private String name;


    @Email(message = "Invalid email id")
    @NotBlank(message = "Email is required!!")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*_+])[A-Za-z\\d!@#$%^&*_+]{8,}$", message = "Invalid password: Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    @NotBlank(message = "Password is required!!")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid gender!!")
    private String gender;

    @NotBlank(message = "about field should not be blank")
    private String about;


    @ImageNameValid
    private String imageName;

}
