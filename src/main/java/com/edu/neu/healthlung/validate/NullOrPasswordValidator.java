package com.edu.neu.healthlung.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NullOrPasswordValidator implements ConstraintValidator<NullOrPassword,Object> {
    @Override
    public void initialize(NullOrPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if( o == null){
            return true;
        }
        boolean flag = false;
        try{
            String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
            Pattern regex = Pattern.compile(reg);
            Matcher matcher = regex.matcher((String)o);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
}
