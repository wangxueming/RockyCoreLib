package com.rockyw.projectcore.service.buriedpoint;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.rockyw.projectcore.common.bean.RedEnvelopCreditBean;

/**
 * 埋点数据存放点
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/3
 */
public interface IBuriedPointService extends IProvider {
    /**
     * 埋点类型: 注册成功——实名处弹“红包&积分增加弹框”
     */
    String BP_REGISTER = "bp_register";
    /**
     * 埋点类型: 实名成功——我的账户处弹“红包&积分增加弹框”
     */
    String BP_CERTIFICATION = "bp_certification";
    /**
     * 埋点类型: 绑卡成功——我的账户处弹“红包&积分增加弹框”
     */
    String BP_BIND_CARD = "bp_bind_card";
    /**
     * 埋点类型: 充值成功——我的账户处弹“积分增加弹框”.
     */
    String BP_RECHARGE = "bp_recharge";
    /**
     * 埋点类型: 投资成功——投资成功页处弹“红包&积分弹窗”
     */
    String BP_INVEST = "bp_invest";
    /**
     * 埋点类型: 登录——首页处弹“积分增加弹框”.
     */
    String BP_LOGIN = "bp_login";
    /**
     * 埋点类型: 查看产品详情→产品详情处弹“积分增加弹框”.
     * 这个埋点数据，不做记录。
     * 因为在产品页获取信息的时候，就直接展示了。不涉及到跨页展示。
     * 不需要额外保存一下
     */
    String BP_SHOW_PRODUCT = "bp_show_product";

    /**
     * 根据类型，设置状态
     * @param type
     * @param bean
     */
    void setBpStatus(String type, RedEnvelopCreditBean bean);

    /**
     * 根据类型获取状态值
     * @param type
     * @return
     */
    RedEnvelopCreditBean getBpStatus(String type);

    /**
     * 关闭对应BpStatus的status状态值，即改为false
     * @param type
     * @return
     */
    RedEnvelopCreditBean turnOffBpStatus(String type);
}
