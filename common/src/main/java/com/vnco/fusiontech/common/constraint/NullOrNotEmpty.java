package com.vnco.fusiontech.common.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint (validatedBy = NullOrNotEmptyValidator.class)
@Repeatable(NullOrNotEmpty.List.class)
public @interface NullOrNotEmpty {
    
    String message() default "Field must be null or not empty";
    
    Class<?>[] groups() default { };
    
    Class<? extends Payload>[] payload() default { };
    
    /**
     * Defines several {@code @NotEmpty} constraints on the same element.
     *
     * @see jakarta.validation.constraints.NotEmpty
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
     @interface List {
        NullOrNotEmpty[] value();
    }
}

class NullOrNotEmptyValidator implements ConstraintValidator<NullOrNotEmpty, Collection<Object>> {
    
    @Override
    public void initialize(NullOrNotEmpty constraintAnnotation) {
        // Initialization, if needed
    }
    
    @Override
    public boolean isValid(Collection<Object> list, ConstraintValidatorContext context) {
        // Allow null values, but ensure non-null values are not blank
        return list == null || !list.isEmpty();
    }
}
