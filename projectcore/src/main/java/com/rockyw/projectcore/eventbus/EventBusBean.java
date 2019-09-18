package com.rockyw.projectcore.eventbus;

import com.rockyw.core.base.BaseEventBusBean;

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
     * 转交页面 - 加减转让比例 - 修改转让比例值
     */
    public static final int T_ACCOUNT_TRANSFER_VALUE_CHANGED = T_PROFILE_REFRESH_MY_NAME + 1;
    /**
     * 转交页面 - 顶部提醒栏被点击了关闭
     */
    public static final int T_ACCOUNT_TRANSFER_NOTIFY_CLOSE = T_ACCOUNT_TRANSFER_VALUE_CHANGED + 1;
    /**
     * 转交页面 - 撤销转交挂单
     */
    public static final int T_ACCOUNT_TRANSFER_CANCEL_TRANSFER = T_ACCOUNT_TRANSFER_NOTIFY_CLOSE + 1;
    /**
     * 转交页面 - 撤销转交挂单
     */
    public static final int T_ACCOUNT_OPEN_TRANSFER_PAGE = T_ACCOUNT_TRANSFER_CANCEL_TRANSFER + 1;
    /**
     * 转交页面 - 撤销转交挂单
     */
    public static final int T_ACCOUNT_PURCHASE_TRANSFER_PAGE = T_ACCOUNT_OPEN_TRANSFER_PAGE + 1;
    /**
     * 投资开始页-点击可用红包-选中了某一个红包后发送进行同步
     */
    public static final int T_PRODUCT_SELECT_RED_ENVELOP = T_ACCOUNT_PURCHASE_TRANSFER_PAGE + 1;
    /**
     * 我的积分页-刷新总积分
     */
    public static final int T_PROFILE_REFRESH_TOTAL_CREDIT = T_PRODUCT_SELECT_RED_ENVELOP + 1;
    /**
     * 收购转让 - 产品转让页的类型切换
     */
    public static final int T_ACCOUNT_TYPE_CALCULATE_PRICE = T_PROFILE_REFRESH_TOTAL_CREDIT + 1;
    /**
     * 收购转让 - 产品转让页的主动设置了价格
     */
    public static final int T_ACCOUNT_SET_NEW_TRANSFER_PRICE = T_ACCOUNT_TYPE_CALCULATE_PRICE + 1;

    /**
     * 产品详情页-展示一级产品详情页
     */
    public static final int T_TRANSFER_PURCHASE_DETAIL_SLIDE_TO_DETAIL = T_ACCOUNT_SET_NEW_TRANSFER_PRICE + 1;
    /**
     * 产品详情页-展示二级产品详情页
     */
    public static final int T_TRANSFER_PURCHASE_DETAIL_SLIDE_TO_DETAIL_MORE = T_TRANSFER_PURCHASE_DETAIL_SLIDE_TO_DETAIL + 1;

    /**
     * 产品详情页-展示一级产品详情页
     */
    public static final int T_PUBLIC_TRANSFER_DETAIL_SLIDE_TO_DETAIL = T_TRANSFER_PURCHASE_DETAIL_SLIDE_TO_DETAIL_MORE + 1;
    /**
     * 产品详情页-展示二级产品详情页
     */
    public static final int T_PUBLIC_TRANSFER_DETAIL_SLIDE_TO_DETAIL_MORE = T_PUBLIC_TRANSFER_DETAIL_SLIDE_TO_DETAIL + 1;
    /**
     * 公开转让-产品详情的数据
     */
    public static final int T_PUBLIC_TRANSFER_DETAIL_SIMPLE_INFO = T_PUBLIC_TRANSFER_DETAIL_SLIDE_TO_DETAIL_MORE + 1;
    /**
     * 转让收购-产品详情的数据
     */
    public static final int T_TRANSFER_PURCHASE_DETAIL_SIMPLE_INFO = T_PUBLIC_TRANSFER_DETAIL_SIMPLE_INFO + 1;


    /**
     * ---------------Logout---------------
     */
    public static final int OTHER_LOGOUT = 90001;


}
