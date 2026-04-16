package com.example.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HospitalFormField {
    String label() default "Field Label";
    String name() default "";
    String placeholder() default "";
    boolean required() default true;
    String type() default "text";
}
