package com.turboic.cloud;

import java.lang.annotation.*;

/**
 * @author liebe
 * 用于标注swagger的类的扫描
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Swagger2Controller {
     String value() default "";
}