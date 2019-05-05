package com.rocky.projectcore.publicdata;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rocky.projectcore.common.router.RouterUrl;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/23
 */
@Route(path = RouterUrl.SERVER_PUBLIC_DATA, name = "公共数据提供器")
public class PublicDataServiceImpl implements IPublicDataService {

    private Context mContext;

    private String mSettlementDataInfo;

    private String mLatestVersionName;

    @Override
    public void init(Context context) {
        mContext = context;
    }

    @Override
    public void setSettlementDataInfo(String info) {
        mSettlementDataInfo = info;
    }

    @Override
    public String getSettlementDataInfo() {
        return mSettlementDataInfo;
    }

    @Override
    public String getLatestVersion() {
        return mLatestVersionName;
    }

    @Override
    public void setLatestVersion(String version) {
        mLatestVersionName = version;
    }

}
