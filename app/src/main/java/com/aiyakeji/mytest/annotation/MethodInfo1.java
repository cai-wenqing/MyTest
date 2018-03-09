package com.aiyakeji.mytest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/3/9 0009.
 * 自定义注解
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)  //运行时注解，自行解析
@Target(ElementType.METHOD)
@Inherited
public @interface MethodInfo1 {

    String name() default "cwq";

    String date();

    int version() default 0;
}
