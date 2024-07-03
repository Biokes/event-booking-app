package com.eventBooker.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class DateValidator implements ConstraintValidator<AfterDateValidator, LocalDateTime> {
    private  String otherFieldName;
    @Override
    public void initialize(AfterDateValidator constraintAnnotation) {
        this.otherFieldName = constraintAnnotation.eventStartStart();
    }
    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext context){
        if(date== null) return false;
        try {
            Field field = context.getClass().getDeclaredField(otherFieldName);
            field.setAccessible(true);
            Object otherObject =  field.get(context.getClass());
            if (otherObject instanceof LocalDateTime)
                return date.isAfter((LocalDateTime) otherObject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
