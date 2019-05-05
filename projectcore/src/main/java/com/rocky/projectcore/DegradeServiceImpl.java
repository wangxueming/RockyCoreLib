package com.rocky.projectcore;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.rocky.projectcore.common.router.RouterUrl;
import com.rocky.core.util.logger.L;

/**
 *
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/28
 */
@Route(path = RouterUrl.SERVER_DEGRADE)
public class DegradeServiceImpl implements DegradeService {
    @Override
    public void onLost(Context context, Postcard postcard) {
        // do something.
        L.i(postcard);
    }

    @Override
    public void init(Context context) {

    }
}