package com.rockyw.core.annotation.winconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 全局的项目配置注解
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/9/18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WinConfig {

    boolean useEventBus() default false;
}
