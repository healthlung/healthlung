package com.edu.neu.healthlung.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrSizeValidator implements ConstraintValidator<NullOrSize,Object> {

    private int min;

    private int max;

    @Override
    public void initialize(NullOrSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if ( o == null){
            return true;
        }
        boolean flag = false;
        try{
            flag = min <= (int)o && (int)o <= max;
        }catch (Exception e){
            flag = false;
        }
        return false;
    }
}
