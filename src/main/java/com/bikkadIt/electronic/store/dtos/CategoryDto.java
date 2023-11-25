package com.bikkadIt.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    @NotBlank
    @Min(value=4, message = "title must be of minimum 4 charters")
    @Max(value = 60)
    private String title;

    @NotBlank(message = "Description required!!")
    @Max(value = 100)
    private String description;

    @NotBlank(message = "coverImage required!!")
    private String coverImage;
}
