package com.rockyw.projectcore.common.router;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/9
 */
public interface CommonAction {
    int COM_ACT_SHOW_BACK_BUTTON_RET_HIDE = 0;
    int COM_ACT_SHOW_BACK_BUTTON_RET_SHOW = 1;
    int COM_ACT_SHOW_BACK_BUTTON_RET_DEF = COM_ACT_SHOW_BACK_BUTTON_RET_SHOW;
    String COM_ACT_SHOW_BACK_BUTTON = "common_act_show_back_button";

    int ACTION_RET_OLD_PWD_BTN_SHOW = 0;
    int ACTION_RET_OLD_PWD_BTN_HIDE = 1;
    int ACTION_RET_OLD_PWD_BTN_DEF = ACTION_RET_OLD_PWD_BTN_SHOW;
    String ACTION_SHOW_OLD_PWD_BTN = "action_show_old_pwd_btn";

    String COM_ACT_DATA_1 = "data1";
    String COM_ACT_DATA_2 = "data2";
    String COM_ACT_DATA_3 = "data3";
    String COM_ACT_DATA_4 = "data4";
    String COM_ACT_DATA_5 = "data5";

    String COM_REQ_DATA_1 = "req_data1";
    String COM_REQ_DATA_2 = "req_data2";
    String COM_REQ_DATA_3 = "req_data3";

    int REQ_PICK_BANK = 101;
    int REQ_BIND_BANK_CARD = 102;
    int REQ_JUMP_TO_RECHARGE = 103;
    int REQ_REGISTER_AGREEMENT = 104;
    int REQ_TRANSFER_FUND = 105;
    int REQ_REFRESH_AFTER_PRODUCT_TRANSFER = 106;
    int REQ_TRANSFER_BUYIN_RISK_AGREEMENT = 107;
    int REQ_TRANSFER_BUYIN_RISK_AGREEMENT2 = 108;

    int HOME_TAB_FIRST = 0;
    int HOME_TAB_PRODUCT = 1;
    int HOME_TAB_ACCOUNT = 2;
    int HOME_TAB_MY = 3;

    int TRANSFER_TYPE_PUBLIC = 1;
    int TRANSFER_TYPE_PURCHASE = 2;
}
