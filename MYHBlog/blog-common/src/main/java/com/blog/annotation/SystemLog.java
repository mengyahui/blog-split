package com.blog.annotation;


import java.lang.annotation.*;

/*
 * @author 孟亚辉
 * @time 2022/9/30 12:50
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    String businessName();
}
