package me.nos.jwtgraphqljava.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotNull
@Pattern.List({
        @Pattern(regexp = ".*?[A-Z].*?", message = "{invalid.password.uppercase}"),
        @Pattern(regexp = ".*?[a-z].*?", message = "{invalid.password.lowercase}"),
        @Pattern(regexp = ".*?[0-9].*?", message = "{invalid.password.digit}"),
        @Pattern(regexp = ".*?[#?!@$%^&*-].*?", message = "{invalid.password.symbol}"),
        @Pattern(regexp = "^\\S{8,}$", message = "{invalid.password.length}")
})
@Target({ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidPassword {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
