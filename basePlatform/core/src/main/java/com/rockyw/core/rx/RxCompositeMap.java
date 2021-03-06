package com.rockyw.core.rx;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 管理 Rx 生命周期
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class RxCompositeMap {
    private Map<Object, CompositeDisposable> compositeDisposableMap;

    private RxCompositeMap() {
        compositeDisposableMap = new HashMap<>();
    }

    public static RxCompositeMap getInstance() {
        return Inner.INSTANCE;
    }

    public synchronized void add(Object tag, Disposable d) {
        if (!compositeDisposableMap.containsKey(tag)) {
            compositeDisposableMap.put(tag, new CompositeDisposable());
        }
        CompositeDisposable compositeDisposable = compositeDisposableMap.get(tag);
        compositeDisposable.add(d);
    }

    public synchronized void clear(Object tag) {
        if (compositeDisposableMap.containsKey(tag)) {
            CompositeDisposable compositeDisposable = compositeDisposableMap.get(tag);
            compositeDisposable.clear();
            compositeDisposableMap.remove(tag);
        }
    }

    private static final class Inner {
        private static RxCompositeMap INSTANCE = new RxCompositeMap();
    }
}