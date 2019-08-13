package com.zbj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bingjia.zheng on 2019/8/13.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BindPath {
    String value();
}
