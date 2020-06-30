package com.edu.neu.healthlung.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Constraint(validatedBy = NullOrPasswordValidator.class)
public @interface NullOrPassword {
    // flag无效时的提示内容
    String message() default "密码不为空且密码格式错误，必须包含字母和数字大于8位";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
