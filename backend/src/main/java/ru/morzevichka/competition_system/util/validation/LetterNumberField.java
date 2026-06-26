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
@NotBlank(message = ValidationMessages.LetterNumber.NOT_BLANK)
@Pattern(
        regexp = ValidationPatterns.LETTER_NUMBER_PATTERN,
        message = ValidationMessages.LetterNumber.INVALID
)
public @interface LetterNumberField {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
