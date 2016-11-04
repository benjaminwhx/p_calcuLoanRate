package com.jd.jr.loan;

import java.lang.annotation.*;

/**
 * User: 吴海旭
 * Date: 2016-11-04
 * Time: 下午2:53
 */
@Documented
@Retention(value = RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Flag {
    String organizationName() default "";
}
