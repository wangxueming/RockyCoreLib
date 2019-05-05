package com.rocky.projectcore.eventbus;

import com.rocky.core.base.BaseEventBusBean;

/**
 * EventBus的消息类型
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/4/4
 */
public class EventBusBean extends BaseEventBusBean<Object> {

    public EventBusBean(int type) {
        this(type, -1);
    }

    public EventBusBean(int type, Object obj) {
        super(type, obj);
    }

    private static final int T_BASE = 10000;
    /**
     * 产品详情页-展示一级产品详情页
     */
    public static final int T_DETAIL_SLIDE_TO_DETAIL = T_BASE + 1;
    /**
     * 产品详情页-展示二级产品详情页
     */
    public static final int T_DETAIL_SLIDE_TO_DETAIL_MORE = T_DETAIL_SLIDE_TO_DETAIL + 1;
    /**
     * 产品详情页-投资时需要传入的bean
     */
    public static final int T_DETAIL_SIMPLE_INFO = T_DETAIL_SLIDE_TO_DETAIL_MORE + 1;
    /**
     * 产品详情页-无网络时  隐藏 计算器和马上投资
     */
    public static final int T_DETAIL_HIDE_CALCULATOR_AND_INVEST_IMM = T_DETAIL_SIMPLE_INFO + 1;
    /**
     * 产品详情页-无网络时  重新展示 计算器和马上投资
     */
    public static final int T_DETAIL_SHOW_CALCULATOR_AND_INVEST_IMM = T_DETAIL_HIDE_CALCULATOR_AND_INVEST_IMM + 1;
    /**
     * 银行卡列表页-点击注销
     */
    public static final int T_BANK_CARD_UNREGISTER = T_DETAIL_SHOW_CALCULATOR_AND_INVEST_IMM + 1;
    /**
     * 我的个人资料页刷新
     */
    public static final int T_PROFILE_REFRESH_MY_PROFILE = T_BANK_CARD_UNREGISTER + 1;
    /**
     * 删除本地加载的图片
     */
    public static final int T_ASSET_PROOF_DELETE_IMAGE = T_PROFILE_REFRESH_MY_PROFILE + 1;
    /**
     * 经过实名认证
     */
    public static final int T_PROFILE_REFRESH_MY_NAME = T_ASSET_PROOF_DELETE_IMAGE + 1;

    /**
     * ---------------Logout---------------
     */
    public static final int OTHER_LOGOUT = 90001;


}
