package com.bikkadIt.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min = 4, message = "title must be of minimum 4 charters")
    private String title;

    @NotBlank(message = "Description required!!")
    @Size(min = 4, message = "Description must be of minimum 4 charters")
    private String description;

    @NotBlank(message = "coverImage required!!")
    private String coverImage;
}
