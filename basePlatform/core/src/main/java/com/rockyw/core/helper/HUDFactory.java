package com.rockyw.core.helper;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

import org.joor.Reflect;

import java.lang.ref.WeakReference;

/**
 * 单一加载进度对话框
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/17
 */
public class HUDFactory {
    private static volatile HUDFactory INSTANCE;
    private WeakReference<KProgressHUD> weakReference;

    private HUDFactory() {}

    public static HUDFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (HUDFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HUDFactory();
                }
            }
        }
        return INSTANCE;
    }


    public KProgressHUD createHUD(Context mContext) {
        KProgressHUD hud;
        if (weakReference != null && weakReference.get() != null && weakReference.get().isShowing()) {
            hud = weakReference.get();
            Context result = Reflect.on(hud).get("mContext");
            if (result == mContext) {
                return hud;
            }
        } else {
            hud = new KProgressHUD(mContext);
            weakReference = new WeakReference<>(hud);
        }
        return hud;
    }

}
