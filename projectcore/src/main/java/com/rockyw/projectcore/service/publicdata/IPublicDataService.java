package com.rockyw.projectcore.service.publicdata;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 公共数据服务，比如说有个两个fragment不好传值，那怎么破呢。放这里传
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/3
 */
public interface IPublicDataService extends IProvider {
    /**
     * 设置预计结算日的描述信息。要么显示date要么就是data desc
     *
     * @param info
     */
    void setSettlementDataInfo(String info);

    /**
     * 获取预计结算日的描述信息
     *
     * @return
     */
    String getSettlementDataInfo();

    /**
     * 获取服务器上最新的版本号
     * @return
     */
    String getLatestVersion();

    /**
     * 根据splash页获取的最新版本信息，保存起来
     *
     * @param version
     */
    void setLatestVersion(String version);
}
