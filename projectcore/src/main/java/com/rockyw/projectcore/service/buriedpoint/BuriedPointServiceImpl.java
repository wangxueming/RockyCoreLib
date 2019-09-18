package com.rockyw.projectcore.service.buriedpoint;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.rockyw.core.util.SPUtil;
import com.rockyw.projectcore.common.bean.RedEnvelopCreditBean;
import com.rockyw.projectcore.common.router.RouterUrl;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/23
 */
@Route(path = RouterUrl.SERVER_BURIED_POINT, name = "红包积分埋点")
public class BuriedPointServiceImpl implements IBuriedPointService {
    private Context mContext;

    private Object mUserLock = new Object();

    @Override
    public void init(Context context) {
        mContext = context;
    }

    @Override
    public void setBpStatus(String type, RedEnvelopCreditBean bean) {
        synchronized (mUserLock) {
            bean.type = type;
            SPUtil.put(mContext, type, JSON.toJSONString(bean));
        }
    }

    @Override
    public RedEnvelopCreditBean getBpStatus(String type) {
        synchronized (mUserLock) {
            return JSON.parseObject(SPUtil.getString(mContext, type, ""), RedEnvelopCreditBean.class);
        }
    }

    @Override
    public RedEnvelopCreditBean turnOffBpStatus(String type) {
        synchronized (mUserLock) {
            RedEnvelopCreditBean result = JSON.parseObject(SPUtil.getString(mContext, type, ""), RedEnvelopCreditBean.class);
            if (result != null && result.status) {
                //#读取之后就将状态重新设置为false
                result.status = false;
                setBpStatus(type, result);
            }
            return result;
        }
    }
}
