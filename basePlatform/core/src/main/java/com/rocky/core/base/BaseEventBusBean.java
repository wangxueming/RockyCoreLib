package com.rocky.core.base;

/**
 * EventBus事件类
 * 统一管理EventBus ,这有缺点,因为EventBus的原理是用链式存储,如果key相同,这有查找的速度就很变慢
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/13
 */
public class BaseEventBusBean<T> {
    private int type;
    private T obj;

    public BaseEventBusBean(int type, T obj) {
        this.type = type;
        this.obj = obj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
