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
@NotBlank(message = ValidationMessages.Login.NOT_BLANK)
@Pattern(
        regexp = ValidationPatterns.LOGIN_PATTERN,
        message = ValidationMessages.Login.INVALID
)
@Size(
        min = ValidationMessages.Login.MIN_SIZE,
        max = ValidationMessages.Login.MAX_SIZE,
        message = ValidationMessages.Login.SIZE
)
public @interface Login {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
