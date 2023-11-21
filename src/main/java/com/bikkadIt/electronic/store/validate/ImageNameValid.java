package com.bikkadIt.electronic.store.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {


    String message() default "Invalid Image Name !!";//error message

    Class<?>[] groups() default {};//represent group constraints

    Class<? extends Payload>[] payload() default {};
    //additional info about error

}
