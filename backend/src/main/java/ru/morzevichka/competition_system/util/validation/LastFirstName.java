package ru.morzevichka.competition_system.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
@NotBlank(message = ValidationMessages.RussianName.NOT_BLANK)
@Size(
        max = ValidationMessages.RussianName.MAX_SIZE,
        message = ValidationMessages.RussianName.SIZE
)
public @interface LastFirstName {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
