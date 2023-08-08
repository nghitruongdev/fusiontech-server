package com.vnco.fusiontech.common.constraint;


import jakarta.validation.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ({ElementType.FIELD, ElementType.PARAMETER})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = NotBlankOrNullValidator.class)
@ReportAsSingleViolation
public @interface NullOrNotBlank {
    String message() default "Field must not be blank when not null";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}

 class NotBlankOrNullValidator implements ConstraintValidator<NullOrNotBlank, String> {
    @Override
    public void initialize(NullOrNotBlank constraintAnnotation) {
        // Initialization, if needed
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Allow null values, but ensure non-null values are not blank
        return value == null || !value.trim().isEmpty();
    }
}
