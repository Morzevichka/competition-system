package ru.morzevichka.competition_system.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
@NotBlank(message = ValidationMessages.Password.NOT_BLANK)
@Size(
        min = ValidationMessages.Password.MIN_SIZE,
        max = ValidationMessages.Password.MAX_SIZE,
        message = ValidationMessages.Password.SIZE
)
@Pattern(
        regexp = ValidationPatterns.PASSWORD_PATTERN,
        message = ValidationMessages.Password.INVALID
)
public @interface Password {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
