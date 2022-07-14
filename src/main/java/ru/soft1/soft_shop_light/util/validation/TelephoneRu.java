package ru.soft1.soft_shop_light.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = TelephoneValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface TelephoneRu {
    String message() default "No valid telephone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
