package com.rockyw.core.annotation.winconfig;

import org.joor.Reflect;
import org.joor.ReflectException;

import javax.inject.Singleton;

/**
 * 项目配置的注解
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/8/21
 */
@Singleton
public class WinConfigProcess {

	public static void bind(final Object object) {
        Class theClass = object.getClass();
        WinConfig annotation = (WinConfig) theClass.getAnnotation(WinConfig.class);
        if (annotation == null) {
            return;
        }
        boolean isUseEvent = annotation.useEventBus();
        try {
            Reflect.on(object).set("isEventBusOn", isUseEvent);
        } catch (ReflectException e) {
            e.printStackTrace();
        }
	}
}
