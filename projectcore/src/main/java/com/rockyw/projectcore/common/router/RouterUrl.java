package com.rockyw.projectcore.common.router;

/**
 * 所有的页面数据都在这里
 *
 * @author wangxueming
 * @version 1.0.0
 * @date 2018/12/20
 */
public interface RouterUrl {
    /**
     * ---------------------登录注册---------------------
     */
    /**
     * 登录页
     */
    String LOGIN_MAIN = "/login/main";

    /**
     * 注册页
     */
    String LOGIN_REGISTER = "/login/register";

    /**
     * 登录协议页
     */
    String LOGIN_AGREEMENT = "/login/agreement";

    /**
     * 找回密码页
     */
    String LOGIN_FORGET_PWD = "/login/forget_pwd";

    /**
     * ---------------------起始页---------------------
     */
    String SPLASH_PAGE = "/main/splash";
    /**
     * ---------------------主页---------------------
     */
    String HOME = "/main/home";
    String FRAG_ROOT_PRODUCT = "/main/fragment_root_product";

    /**
     * ---------------------账户页---------------------
     */
    String FRAG_ACCOUNT = "/account/fragment_account";
    String FRAG_TRANSFER = "/account/fragment_transfer";
    String ACCOUNT_RECHARGE = "/account/recharge";
    String ACCOUNT_WITHDRAW = "/account/withdraw";
    String ACCOUNT_ASSETS_MENU = "/account/assets_menu";
    String ACCOUNT_ASSETS_DETAIL = "/account/assets_detail";
    String ACCOUNT_ASSETS_PRODUCT_SIMPLE_DETAIL = "/account/assets_detail/simple_detail";
    String ACCOUNT_TRANSACTION_DETAIL = "/account/transaction_detail";
    String ACCOUNT_FUNDS_DETAIL = "/account/cash_detail";
    String ACCOUNT_BANK_CARDS = "/account/bank_cards";
    String ACCOUNT_BANK_CARDS_SIMPLE = "/account/bank_cards_simple";
    String ACCOUNT_BIND_BANK_CARD = "/account/bind_bank_card";
    String ACCOUNT_MY_PROFILE = "/account/my_profile";
    String ACCOUNT_PROFILE_RISK_EVALUATION = "/account/risk_evaluation";
    String ACCOUNT_PROFILE_VOUCHER_START = "/account/voucher_start";
    String ACCOUNT_PROFILE_VOUCHER_QUESTIONS = "/account/voucher_questions";
    String ACCOUNT_PROFILE_VOUCHER_END= "/account/voucher_end";
    String ACCOUNT_PROFILE_EMERGENCY_CONTACT = "/account/my_profile/emergency_contact";
    String ACCOUNT_PROFILE_LOGIN_PWD = "/account/my_profile/set_login_pwd";
    String ACCOUNT_PROFILE_TRADE_PWD_MAIN = "/account/my_profile/trans_pwd";
    String ACCOUNT_PROFILE_TRADE_PWD_SET = "/account/my_profile/modify_trade_pwd";
    String ACCOUNT_PROFILE_TRADE_PWD_FIND = "/account/my_profile/find_trade_pwd";
    String ACCOUNT_PROFILE_PHONE_NUMBER = "/account/my_profile/phone_number";
    String ACCOUNT_PROFILE_CERTIFICATION = "/account/my_profile/certification";
    String ACCOUNT_MAIN_TOTAL_ASSETS = "/account/total_assets";
    String ACCOUNT_SHOW_GALLERY = "/account/gallery";
    String ACCOUNT_ASSETS_PROOF = "/account/assets_proof";
    String ACCOUNT_SETTINGS = "/account/settings";
    String ACCOUNT_PRODUCT_PUBLIC_TRANSFER = "/account/product_transfer";
    String ACCOUNT_MY_TRANSFER = "/account/my_transfer";
    String ACCOUNT_TRANSFER_DETAIL = "/account/transfer_detail";
    String ACCOUNT_TRANSFER_BUYIN = "/account/transfer_buyin";
    String ACCOUNT_TRANSFER_INVEST_SUCCESS = "/account/transfer_invest_success";
    String ACCOUNT_TRANSFER_FUNDS = "/account/transfer_funds";
    String ACCOUNT_SECOND_MARKET_MENU = "/account/second_market_menu";
    String ACCOUNT_TRANSFER_PURCHASE = "/account/transfer_purchase";
    String ACCOUNT_TRANSFER_PURCHASE_DETAIL = "/account/transfer_purchase_detail";
    String ACCOUNT_MY_PURCHASE = "/account/my_purchase";
    String ACCOUNT_PRODUCT_PURCHASE_TRANSFER = "/account/product_purchase_transfer";
    String ACCOUNT_RISK_AGREEMENT = "/account/risk_agreement";
    String ACCOUNT_COMPANY_INFO = "/account/company_info";
    String ACCOUNT_TRADE_DETAIL_MENU = "/account/trade_detail_menu";
    String ACCOUNT_TRANSFER_TRADE = "/account/transfer_trade";
    String ACCOUNT_PENDING_ORDER_SUCCESS = "/account/pending_order_success";


    /**
     * ---------------------首页---------------------
     */
    String FRAG_FIRST_PAGE = "/first/frag_frist_page";
    String FIRST_ANNOUNCEMENT_LIST = "/first/announcement_list";
    String FIRST_ANNOUNCEMENT_DETAIL = "/first/announcement_detail";

    /**
     * ---------------------产品---------------------
     */
    String FRAG_PRODUCT = "/product/frag_product";
    String PRODUCT_DETAIL = "/product/detail";
    String PRODUCT_INVESTMENT_START = "/product/investment";
    String PRODUCT_INVESTMENT_INFO_CONFIRM = "/product/investment/info_confirm";
    String PRODUCT_INVESTMENT_SUCCESS = "/product/investment/success";
    String PRODUCT_TRANSFER_PURCHASE_DETAIL = "/product/transfer_purchase_detail2";
    String PRODUCT_PUBLIC_TRANSFER_DETAIL = "/product/public_transfer_detail2";

    /**
     * ---------------------我的---------------------
     */
    String MY_VERSION_INFO = "/my/version_info";

    /**
     * 其他模块
     */
    String OTHERS_SHOW_H5 = "/other/show_h5";
    String OTHERS_JUMP_WEB = "/other/jump_web";
    String OTHERS_DOWNLOAD_APK = "/other/download_apk";
    String OTHERS_JUMP_INSTALL = "/other/jump_install";

    /**
     * 公共模块
     */
    String COMMON_QUALIFY_INVESTOR_AGREEMENT = "/common/qualify_investor_agreement";

    /**
     * service
     */
    String SERVER_ACCOUNT = "/server/account";
    String SERVER_PUBLIC_DATA = "/server/public_data";
    String SERVER_PUBLIC_SERVICE = "/server/public_server";
    String SERVER_BURIED_POINT = "/server/buried_point";

    /**
     * INTERCEPTOR
     */
    String SERVER_LOGIN = "/server/login";
    String SERVER_DEGRADE = "/server/degrade";
    String SERVER_AUTHORIZATION = "/server/authorization";
}
